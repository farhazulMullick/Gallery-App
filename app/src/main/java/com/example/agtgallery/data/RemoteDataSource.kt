package com.example.agtgallery.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.agtgallery.apiService.FlickrApi
import com.example.agtgallery.util.FlickrPagingSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val flickrApi: FlickrApi) {

    fun getPhotos(query: HashMap<String, String>) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {FlickrPagingSource(flickrApi, query)}
        ).liveData
}