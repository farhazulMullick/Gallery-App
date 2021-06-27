package com.example.agtgallery.data

import com.example.agtgallery.apiService.FlickrApi
import com.example.agtgallery.modals.FlickrData
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val flickrApi: FlickrApi) {

    suspend fun getPhotos(query: HashMap<String, String>): Response<FlickrData>{
        return flickrApi.getPhotos(query)
    }
}