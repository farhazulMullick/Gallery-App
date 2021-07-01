package com.example.agtgallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.agtgallery.R
import com.example.agtgallery.databinding.HomeRowLayoutBinding
import com.example.agtgallery.fragment.HomeFragmentDirections
import com.example.agtgallery.modals.Photo
import kotlinx.android.synthetic.main.home_row_layout.view.*

class FlickrImageAdapter :
    PagingDataAdapter<Photo, FlickrImageAdapter.MyViewHolder>(flickrCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            HomeRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPhoto = getItem(position)
        if (currentPhoto != null) {
            holder.bind(currentPhoto)
        }
    }

    class MyViewHolder(private val binding: HomeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urlS)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_no_data)
                    .into(homeImageView)
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsActivity(photo)
                homeRowCardView.setOnClickListener {
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    companion object {
        private val flickrCallBack = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem

        }
    }
}