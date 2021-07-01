package com.example.agtgallery.util

import android.util.Log
import androidx.paging.PagingSource
import com.example.agtgallery.apiService.FlickrApi
import com.example.agtgallery.modals.Photo
import com.example.agtgallery.util.Constants.INITIAL_PAGE
import retrofit2.HttpException
import java.io.IOException


class FlickrPagingSource(
    val flickrApi: FlickrApi,
    val queryMap: HashMap<String, String>
    ): PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: INITIAL_PAGE
        Log.i("Position", "$page")

        return try {
            val response = flickrApi.getPhotos(
                page,
                params.loadSize,
                queryMap
            )
            val photoList = response.photos.photo
            LoadResult.Page(
                data = photoList,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = page.plus(1)
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException){
            LoadResult.Error(e)
        }
    }


}