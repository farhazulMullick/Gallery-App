package com.example.agtgallery.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.agtgallery.data.Repository
import com.example.agtgallery.modals.FlickrData
import com.example.agtgallery.modals.Photo
import com.example.agtgallery.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        application: Application,
        private val repository: Repository
) : AndroidViewModel(application) {

        private var _flickrResponse: MutableLiveData<PagingData<Photo>> = MutableLiveData()
        val flickResponse: LiveData<PagingData<Photo>> get() = _flickrResponse

        fun getPhotos(query: HashMap<String, String>){
                viewModelScope.launch{
                        getPhotosSafeCall(query)
                }
        }

        private fun getPhotosSafeCall(query: HashMap<String, String>) {
                if (hasInternetConnection()){
                        val response = repository.remote.getPhotos(query)
                        _flickrResponse = response as MutableLiveData<PagingData<Photo>>
                        _flickrResponse.cachedIn(viewModelScope)
                }else{

                }
        }

        private fun handleResponse(response: Response<FlickrData>): NetworkResult<FlickrData>? {
                return when{
                        response.code() == 100 -> {
                                NetworkResult.Error("Invalid api key")
                        }

                        response.code() == 105 -> {
                                NetworkResult.Error("Service currently unavailable")
                        }

                        response.body().toString().isNullOrEmpty()->{
                                NetworkResult.Error("Sorry, no photos found")
                        }

                        response.body()?.photos?.total == 0 ->{
                                NetworkResult.Error("Sorry, no photos found")
                        }

                        response.isSuccessful->{
                                val data = response.body()!!
                                NetworkResult.Success(data)
                        }
                        else -> {
                                NetworkResult.Error("Sorry, Some thing went wrong...")
                        }
                }
        }

        private fun hasInternetConnection(): Boolean{
                val cm = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
                        return when{
                                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                                else -> false
                        }
                }
                else {
                        val networkInfo : NetworkInfo? = cm.activeNetworkInfo
                        return networkInfo?.isConnectedOrConnecting == true
                }
        }
}