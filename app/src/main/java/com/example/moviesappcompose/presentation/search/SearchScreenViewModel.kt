package com.example.moviesappcompose.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappcompose.data.remote.MoviesApiManager
import com.example.moviesappcompose.data.remote.Result
import com.example.moviesappcompose.domain.Constant.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val apiManager: MoviesApiManager) :
    ViewModel() {
    var searchResultMovies = mutableStateOf<List<Result>>(emptyList())

    fun searchMovie(movieName: String) {
        println("searchhhhhhh")
        try {
            viewModelScope.launch(Dispatchers.IO) {
                searchResultMovies.value =
                    apiManager.searchMovie(apiKey = API_KEY, query = movieName).results
            }
        } catch (e: Exception) {
            println("Errorrrrrr: ${e.message}")
        }

    }

    fun searchWithGenreMovie(genreId: Int) {
        println("searchhhhhhh")
        try {
            viewModelScope.launch(Dispatchers.IO) {
                searchResultMovies.value =
                    apiManager.getMoviesWithGenre(apiKey = API_KEY, genreId = genreId.toString()).results
            }
        } catch (e: Exception) {
            println("Errorrrrrr: ${e.message}")
        }

    }
}