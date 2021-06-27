package com.example.agtgallery.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.agtgallery.R
import com.example.agtgallery.adapter.ImageAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
    private lateinit var myView: View
    private val imageAdapter = ImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false)
        setUpRecyclerView()
        return myView
    }

    private fun setUpRecyclerView() {
        myView.rv_home.apply {
            adapter = imageAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }

        imageAdapter.setData(fakeData())
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