package com.example.moviesappcompose.data.remote

import com.example.moviesappcompose.domain.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiManager {
    @GET("movie/popular")
    suspend fun getTopMovie(
        @Header("Authorization") apiKey: String
    ): RemoteMovie

    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Header("Authorization") apiKey: String
    ): RemoteMovie

    @GET("movie/top_rated")
    suspend fun getRecommendedMovies(
        @Header("Authorization") apiKey: String
    ): RemoteMovie

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Header("Authorization") apiKey: String,
        @Path("movie_id") movieId: Int
    ): MovieDetails

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationsForSpecificMovie(
        @Header("Authorization") apiKey: String,
        @Path("movie_id") movieId: Int
    ): RemoteMovie

    @GET("search/movie")
    suspend fun searchMovie(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String
    ): RemoteMovie

    @GET("discover/movie")
    suspend fun getMoviesWithGenre(
        @Header("Authorization") apiKey: String,
        @Query("with_genres") genreId: String
    ): RemoteMovie

    @GET("genre/movie/list")
    suspend fun getMoviesCategories(
        @Header("Authorization") apiKey: String,
    ): MovieCategories
}