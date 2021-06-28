package com.example.agtgallery.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.agtgallery.util.Constants.API_KEY
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class HomeViewModel @Inject constructor(
        application: Application
): AndroidViewModel(application) {

    fun applyQuery(): HashMap<String, String>{
        val query = HashMap<String, String>()
        query["api_key"] = API_KEY
        query["method"] = "flickr.photos.getRecent"
        query["per_page"] = "20"
        query["page"] = "1"
        query["format"] = "json"
        query["nojsoncallback"] = "1"
        query["extras"] = "url_s"

        return query
    }
}