package com.example.agtgallery.apiService

import com.example.agtgallery.modals.FlickrData
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FlickrApi {
    @GET("/services/rest")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,

        @QueryMap query: HashMap<String, String>

    ): FlickrData
}