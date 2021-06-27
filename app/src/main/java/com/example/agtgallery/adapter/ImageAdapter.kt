package com.example.agtgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.agtgallery.R
import kotlinx.android.synthetic.main.home_row_layout.view.*

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    private var imageUrlList = emptyList<String>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUrl = imageUrlList[position]
        holder.itemView.apply {
            Glide.with(this).load(currentUrl).into(home_image_view)
        }
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    fun setData(imgUrl: List<String>){
        imageUrlList = imgUrl
        notifyDataSetChanged()
    }
}