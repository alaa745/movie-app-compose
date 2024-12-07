package com.example.moviesappcompose.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesappcompose.R
import com.example.moviesappcompose.presentation.components.HorizontalMovieCard
import com.example.moviesappcompose.presentation.components.SearchField

@Composable
fun SearchScreen(movieGenre: Int? = 0) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val viewModel: SearchScreenViewModel = hiltViewModel()
    if (movieGenre != 0)
        viewModel.searchWithGenreMovie(genreId = movieGenre!!)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF121312))
            .statusBarsPadding()
//                        .navigationBarsPadding()
            .padding(10.dp),
    ) {
        SearchField(
            onValueChange = {
                searchQuery = it
            },
            value = searchQuery,
        ) {
            println("queryyyyy $it")
            viewModel.searchMovie(movieName = it)
        }
        if (viewModel.searchResultMovies.value.isEmpty())
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_movies),
                    contentDescription = null
                )
                Text(text = "No Movies Found" , fontSize = 15.sp , color = Color.White , modifier = Modifier.padding(top = 10.dp))
            }
        LazyColumn(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            items(viewModel.searchResultMovies.value) {
                HorizontalMovieCard(movie = it)
            }
        }
    }
}