package com.example.agtgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agtgallery.R
import com.example.agtgallery.fragment.HomeFragmentDirections
import com.example.agtgallery.fragment.SeearchFragmentDirections
import com.example.agtgallery.modals.FlickrData
import com.example.agtgallery.modals.Photo
import com.example.agtgallery.modals.Photos
import kotlinx.android.synthetic.main.home_row_layout.view.*

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    private var imageUrlList = emptyList<Photo>()
    private lateinit var photos: Photos
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentImage = imageUrlList[position]
        holder.itemView.apply {
            Glide.with(this).load(currentImage.urlS).into(home_image_view)

            home_row_card_view.setOnClickListener {
                val homeAction = HomeFragmentDirections.actionHomeFragmentToDetailsActivity(currentImage)
                findNavController().navigate(homeAction)
            }
        }
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    fun setData(imgUrl: FlickrData){
        imageUrlList = imgUrl.photos.photo
        photos = imgUrl.photos
        notifyDataSetChanged()
    }
}