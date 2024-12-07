package com.example.moviesappcompose.presentation.movieDetails

import com.example.moviesappcompose.domain.Movie
import com.example.moviesappcompose.domain.MovieDetails

sealed class MovieDetailsScreenState {
    object Initial: MovieDetailsScreenState()
    data class GetMovieDetailsSuccessState(val movie: MovieDetails): MovieDetailsScreenState()
    data class GetMovieDetailsLoadingState(val message: String): MovieDetailsScreenState()
    data class GetMovieDetailsRecommendationsSuccessState(val movies: List<Movie>): MovieDetailsScreenState()
    data class GetMovieDetailsRecommendationsLoadingState(val message: String): MovieDetailsScreenState()
}