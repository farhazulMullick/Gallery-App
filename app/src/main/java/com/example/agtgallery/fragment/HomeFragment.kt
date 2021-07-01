package com.example.agtgallery.fragment

import android.accounts.NetworkErrorException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.agtgallery.R
import com.example.agtgallery.adapter.FlickrImageAdapter
import com.example.agtgallery.viewmodels.HomeViewModel
import com.example.agtgallery.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var myView: View
    private val flickrAdapter = FlickrImageAdapter()

    private val mainViewModel by viewModels<MainViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        setUpRecyclerView()
        requestApi()

        myView.retry_btn.setOnClickListener {
            requestApi()
        }

        return myView
    }

    private fun requestApi() {
        mainViewModel.getPhotos(homeViewModel.applyQuery())
        mainViewModel.flickResponse.observe(viewLifecycleOwner, { response->
            try {
                Toast.makeText(requireContext(), "response recieved", Toast.LENGTH_SHORT).show()
                flickrAdapter.submitData(viewLifecycleOwner.lifecycle, response)
                hideShimmer()
                hideEmptyStates()
            }catch (e: NetworkErrorException){
                showEmptyStates()
                hideShimmer()
            }catch (e: IOException){
                hideShimmer()
            }catch (e: HttpException){
                showEmptyStates()
                hideShimmer()
            }

        })
    }


    private fun setUpRecyclerView() {
        myView.rv_home.apply {
            adapter = flickrAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
        showShimmer()
        hideEmptyStates()
    }

    private fun showShimmer(){
        myView.rv_home.showShimmer()
    }

    private fun hideShimmer(){
        myView.rv_home.hideShimmer()
    }

    private fun hideEmptyStates(){
        myView.apply {
            retry_btn.visibility = View.INVISIBLE
            nodata_img_view.visibility = View.INVISIBLE
            nodata_text_view.visibility = View.INVISIBLE
            home_title_txt.visibility = View.VISIBLE
            rv_home.visibility = View.VISIBLE
        }
    }

    private fun showEmptyStates(){
        myView.apply {
            retry_btn.visibility = View.VISIBLE
            nodata_img_view.visibility = View.VISIBLE
            nodata_text_view.visibility = View.VISIBLE
            home_title_txt.visibility = View.INVISIBLE
            rv_home.visibility = View.INVISIBLE
        }
    }


    private fun fakeData(): List<String>{
        return listOf(
            "https://live.staticflickr.com/65535/51273328747_3f2327ee43_m.jpg",
            "https://live.staticflickr.com/65535/51273329137_964b1847c5_m.jpg",
            "https://live.staticflickr.com/65535/51274077416_0514b06ea8_m.jpg",
            "https://live.staticflickr.com/65535/51274251463_46d731c252_m.jpg",
            "https://live.staticflickr.com/65535/51274251643_7f6cb81316_m.jpg",
            "https://live.staticflickr.com/65535/51274805434_61d3be1699_m.jpg",
            "https://live.staticflickr.com/65535/51275105390_0f3a622435_m.jpg",
            "https://live.staticflickr.com/65535/51275106235_d86596f160_m.jpg"
        )
    }

}