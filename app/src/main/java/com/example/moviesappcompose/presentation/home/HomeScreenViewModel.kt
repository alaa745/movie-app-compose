package com.example.moviesappcompose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappcompose.data.local.LocalMovie
import com.example.moviesappcompose.data.local.MoviesDao
import com.example.moviesappcompose.data.remote.MoviesApiManager
import com.example.moviesappcompose.data.remote.Result
import com.example.moviesappcompose.domain.Constant.API_KEY
import com.example.moviesappcompose.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private var moviesApiManager: MoviesApiManager,
    private var moviesDao: MoviesDao
) : ViewModel() {
    var topMoviesState by mutableStateOf<HomeScreenState>(HomeScreenState.Initial)
    var latestMovieState by mutableStateOf<HomeScreenState>(HomeScreenState.Initial)
    var favoriteMovies by mutableStateOf<List<LocalMovie>>(emptyList())
    var latestMoviesList by mutableStateOf<List<Movie>>(emptyList())
    var topMoviesList by mutableStateOf<List<Result>>(emptyList())
    var recommendedMoviesList by mutableStateOf<List<Movie>>(emptyList())
    var recommendedMoviesState by mutableStateOf<HomeScreenState>(HomeScreenState.Initial)

    init {
        getHomeScreenMovies()
    }

    private fun getHomeScreenMovies() {
        topMoviesState = HomeScreenState.GetTopMoviesLoadingState("loading")
//        topMoviesState = HomeScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovies = moviesDao.getFavoriteMovies()

            try {
                val topMovies = moviesApiManager.getTopMovie(API_KEY)
                topMoviesList = topMovies.results
                withContext(Dispatchers.Main) {
                    topMoviesState = HomeScreenState.GetTopMovieSuccessState(movie = topMovies)
                }
            } catch (e: Exception) {
                throw e
            }

            try {
                val latestMovie = moviesApiManager.getLatestMovies(API_KEY)
                latestMoviesList = latestMovie.results.map { movie ->
                    Movie(
                        id = movie.id,
                        isFavorite = false,
                        adult = movie.adult,
                        backdropPath = movie.backdropPath,
                        popularity = movie.popularity,
                        posterPath = movie.posterPath,
                        overview = movie.overview,
                        releaseDate = movie.releaseDate,
                        title = movie.title,
                        originalLanguage = movie.originalLanguage,
                        originalTitle = movie.originalTitle,
                        video = movie.video,
                        voteCount = movie.voteCount,
                        voteAverage = movie.voteAverage,
                        genreIds = movie.genreIds
                    )
                }
                withContext(Dispatchers.Main) {
                    latestMovieState =
                        HomeScreenState.GetNowPlayingMoviesSuccessState(
                            movie = latestMoviesList
                        )
                }
            } catch (e: Exception) {
                throw e
            }
            try {
                recommendedMoviesState = HomeScreenState.GetRecommendedMoviesLoadingState(message = "Loading")
                val recommendedList = moviesApiManager.getRecommendedMovies(API_KEY)
                recommendedMoviesList = recommendedList.results.map { movie ->
                    Movie(
                        id = movie.id,
                        isFavorite = false,
                        adult = movie.adult,
                        backdropPath = movie.backdropPath,
                        popularity = movie.popularity,
                        posterPath = movie.posterPath,
                        overview = movie.overview,
                        releaseDate = movie.releaseDate,
                        title = movie.title,
                        originalLanguage = movie.originalLanguage,
                        originalTitle = movie.originalTitle,
                        video = movie.video,
                        voteCount = movie.voteCount,
                        voteAverage = movie.voteAverage,
                        genreIds = movie.genreIds
                    )
                }
                withContext(Dispatchers.Main){
                    recommendedMoviesState = HomeScreenState.GetRecommendedMoviesSuccessState(movie = recommendedMoviesList)
                }

            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        val isMovieExist = favoriteMovies.any { it.id == movie.id }
        viewModelScope.launch(Dispatchers.IO) {
            if (isMovieExist)
                favoriteMovies.firstOrNull { it.id == movie.id }?.let { moviesDao.deleteMovie(it) }
            else
                moviesDao.insertMovie(
                    LocalMovie(
                        id = movie.id,
                        isFavorite = true,
                        adult = movie.adult,
                        backdropPath = movie.backdropPath ?: "",
                        popularity = movie.popularity,
                        posterPath = movie.posterPath,
                        overview = movie.overview,
                        releaseDate = movie.releaseDate,
                        title = movie.title,
                        originalLanguage = movie.originalLanguage,
                        originalTitle = movie.originalTitle,
                        video = movie.video,
                        voteCount = movie.voteCount,
                        voteAverage = movie.voteAverage,
                    )
                )

            val updatedList = moviesDao.getFavoriteMovies()
            withContext(Dispatchers.Main) { favoriteMovies = updatedList }

        }
    }

    fun checkIsFavorite(movie: Movie): Boolean {
        println("aaaaaaaaa")
//        viewModelScope.launch(Dispatchers.IO) {
//            favoriteMovies = moviesDao.getFavoriteMovies()
//        }

        return favoriteMovies.any { it.id == movie.id }
    }
}