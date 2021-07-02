package com.example.agtgallery.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.agtgallery.R
import com.example.agtgallery.adapter.ImageAdapter
import com.example.agtgallery.adapter.SearchImageAdapter
import com.example.agtgallery.databinding.FragmentSeearchBinding
import com.example.agtgallery.util.NetworkResult
import com.example.agtgallery.viewmodels.MainViewModel
import com.example.agtgallery.viewmodels.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class SeearchFragment : Fragment() {
    private var _binding: FragmentSeearchBinding? = null
    private val binding get() = _binding!!
    private val disposables = CompositeDisposable()
    private var timeSinceLastRequest: Long = 0

    private val searchViewModel by viewModels<SearchViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()

    private val searchAdapter = SearchImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeearchBinding.inflate(layoutInflater)
        timeSinceLastRequest = System.currentTimeMillis()
        setUpSearchView()
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewSearch.apply {
            adapter = searchAdapter
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun setUpSearchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(1000)
                    newText?.let{
                        if(!it.isNullOrEmpty()){
                            Log.d("SearchView", "$it")
                            mainViewModel.getPhotos(searchViewModel.applyQuery(it))
                            mainViewModel.flickrResponse.observe(viewLifecycleOwner, Observer {response ->
                                Log.d("Response", "${response.data}")
                                if(response != null){
                                    when(response){
                                        is NetworkResult.Loading ->{
                                            hideEmptyStates()
                                        }
                                        is NetworkResult.Success -> {
                                            hideEmptyStates()
                                            searchAdapter.setData(response.data!!)
                                        }
                                        is NetworkResult.Error -> {
                                            showEmptyStates()
                                            showRetrySnackBar("Sorry ${response.message!!}" )
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
                return true
            }

        })
    }

    private fun showRetrySnackBar(message: String){
        Snackbar.make(
            binding.contextView,
            message,
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun hideEmptyStates(){
        binding.apply {
            searchNoDataImg.visibility = View.GONE
            recyclerViewSearch.visibility = View.VISIBLE
        }
    }

    private fun showEmptyStates(){
        binding.apply {
            searchNoDataImg.setImageResource(R.drawable.ic_no_internet)
            searchNoDataImg.visibility = View.VISIBLE
            recyclerViewSearch.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}