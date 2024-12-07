package com.example.moviesappcompose.presentation.browse

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappcompose.data.remote.Genre
import com.example.moviesappcompose.data.remote.MoviesApiManager
import com.example.moviesappcompose.domain.Constant.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseScreenViewModel @Inject constructor(private val apiManager: MoviesApiManager): ViewModel(){
    var moviesCategoriesList = mutableStateOf<List<Genre>>(emptyList())

    init {
        viewModelScope.launch (Dispatchers.IO){
            moviesCategoriesList.value = apiManager.getMoviesCategories(API_KEY).genres
        }
    }
}