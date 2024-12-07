package com.example.moviesappcompose.presentation.browse

import androidx.compose.runtime.Composable
import com.example.moviesappcompose.presentation.search.SearchScreen

@Composable
fun CategorizedMoviesScreen(genreId: Int){
    SearchScreen(movieGenre = genreId)
}