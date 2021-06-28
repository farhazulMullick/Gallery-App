package com.example.agtgallery.modals


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class Photos(
        @SerializedName("page")
    val page: Int,
        @SerializedName("pages")
    val pages: Int,
        @SerializedName("perpage")
    val perpage: Int,
        @SerializedName("photo")
    val photo: List<Photo>,
        @SerializedName("total")
    val total: Int
)