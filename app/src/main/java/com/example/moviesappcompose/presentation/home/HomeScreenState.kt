package com.example.moviesappcompose.presentation.home

import com.example.moviesappcompose.data.remote.RemoteMovie
import com.example.moviesappcompose.domain.Movie

sealed class HomeScreenState {
    object Initial : HomeScreenState()
    data class GetTopMovieSuccessState(val movie: RemoteMovie): HomeScreenState()
    data class GetNowPlayingMoviesSuccessState(val movie: List<Movie>): HomeScreenState()
    data class GetTopMoviesLoadingState(val message: String): HomeScreenState()
    data class GetRecommendedMoviesSuccessState(val movie: List<Movie>): HomeScreenState()
    data class GetRecommendedMoviesLoadingState(val message: String): HomeScreenState()

}