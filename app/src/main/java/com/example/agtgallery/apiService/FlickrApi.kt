package com.example.agtgallery.apiService

import com.example.agtgallery.modals.FlickrData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FlickrApi {
    @GET("/services/rest")
    suspend fun getPhotos(
        @QueryMap
        query: HashMap<String, String>
    ): Response<FlickrData>
}