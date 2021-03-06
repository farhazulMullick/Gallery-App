package com.example.agtgallery.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentStateManagerControl
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.agtgallery.R
import com.example.agtgallery.adapter.ImageAdapter
import com.example.agtgallery.util.NetworkResult
import com.example.agtgallery.viewmodels.HomeViewModel
import com.example.agtgallery.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var myView: View
    private val imageAdapter = ImageAdapter()

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
        mainViewModel.flickrResponse.observe(viewLifecycleOwner, {response->
            when(response){
                is NetworkResult.Loading ->{
                    showShimmer()
                    hideEmptyStates()
                }
                is NetworkResult.Success ->{
                    response.data!!.let { imageAdapter.setData(it) }
                    hideShimmer()

                }
                is NetworkResult.Error ->{
                    when(response.message){
                        "No, Internet Connection" -> {
                            nodata_img_view.setImageResource(R.drawable.ic_no_internet)
                        }
                        "Sorry, no photos found" ->{
                            nodata_img_view.setImageResource(R.drawable.ic_no_data)
                        }
                    }

                    nodata_text_view.text = response.message.toString()
                    hideShimmer()
                    showEmptyStates()
                    showRetrySnackBar(response.message!!)
                }
            }

        })
    }

    private fun showRetrySnackBar(message: String){
        Snackbar.make(
            context_view,
            message,
            Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                requestApi()
            }
            .show()
    }


    private fun setUpRecyclerView() {
        myView.rv_home.apply {
            adapter = imageAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
        showShimmer()
    }

    private fun showShimmer(){
        myView.rv_home.showShimmer()
    }

    private fun hideShimmer(){
        myView.rv_home.hideShimmer()
    }

    private fun hideEmptyStates(){
        myView.apply {
            retry_btn.visibility = View.GONE
            nodata_img_view.visibility = View.INVISIBLE
            nodata_text_view.visibility = View.INVISIBLE
            home_title_txt.visibility = View.VISIBLE
            rv_home.visibility = View.VISIBLE
        }
    }

    private fun showEmptyStates(){
        myView.apply {
            retry_btn.visibility = View.GONE
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