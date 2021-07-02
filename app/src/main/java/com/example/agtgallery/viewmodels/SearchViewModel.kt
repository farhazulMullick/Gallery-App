package com.example.agtgallery.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.agtgallery.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    fun applyQuery(searchQuery: String): HashMap<String, String> {
        val query = HashMap<String, String>()
        query["text"] = searchQuery
        query["api_key"] = Constants.API_KEY
        query["method"] = "flickr.photos.search"
        query["per_page"] = "20"
        query["page"] = "1"
        query["format"] = "json"
        query["nojsoncallback"] = "1"
        query["extras"] = "url_s"

        return query
    }
}