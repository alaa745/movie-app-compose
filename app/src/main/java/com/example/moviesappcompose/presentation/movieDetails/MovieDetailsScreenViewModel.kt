package com.example.moviesappcompose.presentation.movieDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappcompose.data.local.LocalMovie
import com.example.moviesappcompose.data.local.MoviesDao
import com.example.moviesappcompose.data.remote.MoviesApiManager
import com.example.moviesappcompose.domain.Constant.API_KEY
import com.example.moviesappcompose.domain.Movie
import com.example.moviesappcompose.domain.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    val apiManager: MoviesApiManager,
    val moviesDao: MoviesDao
) : ViewModel() {
    var movieDetailsState = MutableLiveData<MovieDetailsScreenState>(MovieDetailsScreenState.Initial)
    var movieRecommendationsState = MutableLiveData<MovieDetailsScreenState>(MovieDetailsScreenState.Initial)
    var recommendedMovies by mutableStateOf<List<Movie>>(emptyList())
    var movie by mutableStateOf<MovieDetails?>(null)
    var favoriteMovies by mutableStateOf<List<LocalMovie>>(emptyList())

    fun getMovieDetails(movieId: Int) {
        try {
            movieDetailsState.value =
                MovieDetailsScreenState.GetMovieDetailsLoadingState(message = "Loading")

            viewModelScope.launch(Dispatchers.IO) {
                movie = apiManager.getMovieDetails(apiKey = API_KEY, movieId = movieId)
                println("movieeeeee Id ${movie?.id}")
                withContext(Dispatchers.Main) {
                    movieDetailsState.value =
                        MovieDetailsScreenState.GetMovieDetailsSuccessState(movie = movie!!)
                }
            }
            getMovieRecommendations(movieId = movieId)

        } catch (e: Exception) {
            throw e
        }
    }

    private fun getMovieRecommendations(movieId: Int) {
        try {
            movieRecommendationsState.value =
                MovieDetailsScreenState.GetMovieDetailsRecommendationsLoadingState(message = "Loading")

            viewModelScope.launch(Dispatchers.IO) {
                val recommendedResponse = apiManager.getRecommendationsForSpecificMovie(apiKey = API_KEY, movieId = movieId)
                recommendedMovies = recommendedResponse.results.map {  movie ->
                    Movie(
                        id = movie.id,
                        isFavorite = false,
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
                        genreIds = movie.genreIds
                    )
                }
                println("movieeeeee Id ${movie?.id}")
                withContext(Dispatchers.Main) {
                    movieRecommendationsState.value =
                        MovieDetailsScreenState.GetMovieDetailsRecommendationsSuccessState(movies = recommendedMovies)
                }
            }

        } catch (e: Exception) {
            throw e
        }
    }

    fun toggleFavoriteMovie(movie: MovieDetails) {
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
                        backdropPath = movie.backdrop_path,
                        popularity = movie.popularity,
                        posterPath = movie.poster_path,
                        overview = movie.overview,
                        releaseDate = movie.release_date,
                        title = movie.title,
                        originalLanguage = movie.original_language,
                        originalTitle = movie.original_title,
                        video = movie.video,
                        voteCount = movie.vote_count,
                        voteAverage = movie.vote_average,
                    )
                )

            val updatedList = moviesDao.getFavoriteMovies()
            withContext(Dispatchers.Main) { favoriteMovies = updatedList }

        }
    }

    fun checkIsFavorite(movieId: Int): Boolean {
//        viewModelScope.launch(Dispatchers.IO) {
//            favoriteMovies = moviesDao.getFavoriteMovies()
//        }
        return favoriteMovies.any { it.id == movieId }
    }
}