package com.example.agtgallery.data

import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource) {

    val remote = remoteDataSource
}