package com.example.agtgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agtgallery.R
import com.example.agtgallery.modals.Photo
import com.example.agtgallery.modals.Photos
import kotlinx.android.synthetic.main.details_row_layout.view.*

class DetailsImageAdapter: RecyclerView.Adapter<DetailsImageAdapter.MyViewHolder>() {

    private var imgData = emptyList<Photo>()
    private var index = 0

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsImageAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_row_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsImageAdapter.MyViewHolder, position: Int) {
        val currentPhoto = imgData[position]
        holder.itemView.apply{
            Glide.with(this).load(currentPhoto.urlS).into(details_row_image_view)
        }
    }

    override fun getItemCount(): Int {
        return imgData.size
    }

    fun setData(newData: Photos, pos: Int){
        imgData = newData.photo
        index = pos
        notifyDataSetChanged()
    }


}