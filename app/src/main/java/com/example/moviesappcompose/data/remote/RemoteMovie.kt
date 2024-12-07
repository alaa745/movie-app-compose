package com.example.moviesappcompose.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteMovie(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)