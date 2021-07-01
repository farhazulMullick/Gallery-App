package com.example.agtgallery.modals
import com.example.agtgallery.modals.Photos
import com.google.gson.annotations.SerializedName

data class FlickrData(
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("stat")
    val stat: String
)