package com.example.agtgallery.adapter

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.agtgallery.R
import com.example.agtgallery.databinding.HomeRowLayoutBinding
import com.example.agtgallery.fragment.SeearchFragmentDirections
import com.example.agtgallery.modals.FlickrData
import com.example.agtgallery.modals.Photo

class SearchImageAdapter: RecyclerView.Adapter<SearchImageAdapter.MyViewHolder>() {

    private var imageUrlList = emptyList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            HomeRowLayoutBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPhoto = imageUrlList[position]
        holder.bind(currentPhoto)
    }

    override fun getItemCount(): Int = imageUrlList.size

    class MyViewHolder(
        private val binding: HomeRowLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo){
            binding.apply {
                Glide.with(itemView.context)
                    .load(photo.urlS)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_no_data)
                    .into(homeImageView)

                homeRowCardView.setOnClickListener {
                    val action = SeearchFragmentDirections.actionSearchFragmentToDetailsActivity(photo)
                    itemView.findNavController().navigate(action)
                }
            }
        }

    }

    fun setData(newData: FlickrData){
        imageUrlList = newData.photos.photo
        notifyDataSetChanged()
    }
}