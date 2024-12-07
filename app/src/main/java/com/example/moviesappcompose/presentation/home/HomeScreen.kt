package com.example.moviesappcompose.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesappcompose.domain.Movie
import com.example.moviesappcompose.presentation.components.RecommendedCard
import com.example.moviesappcompose.presentation.components.VerticalMovieCard

@Composable
fun HomeScreen(
    topState: HomeScreenState,
    latestState: HomeScreenState,
    recommendedState: HomeScreenState,
    checkIsFavorite: (Movie) -> Boolean,
    onFavoriteClicked: (Movie) -> Unit,
    onMovieClicked: (Int) -> Unit
) {
    LazyColumn(
//        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .background(Color(0xFF121312))
            .fillMaxSize()
//            .navigationBarsPadding()
    ) {
        item {
            Box(
                contentAlignment = Alignment.TopStart, modifier = Modifier.wrapContentSize()
            ) {
                when (topState) {
                    is HomeScreenState.GetTopMovieSuccessState -> {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${topState.movie.results.first().backdropPath}",
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.height(220.dp)
                        )
                        VerticalMovieCard(
                            movie = topState.movie.results.map { movie ->
                                Movie(
                                    id = movie.id,
                                    isFavorite = movie.isFavorite,
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
                            }.first(),
                            height = 200,
                            width = 140,
                            modifier = Modifier.padding(start = 12.dp, top = 100.dp),
                            checkIsFavorite = {
                                checkIsFavorite(it)
                            },
                            onFavoriteClicked = {
                            }
                        ) {
                            onMovieClicked(it)
                        }
                    }

                    is HomeScreenState.GetTopMoviesLoadingState -> CircularProgressIndicator()
                    else -> Unit
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .background(color = Color(0xFF282A28))
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Now Playing",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 15.dp, top = 15.dp)
                    )
                    when (latestState) {
                        is HomeScreenState.GetNowPlayingMoviesSuccessState -> {
                            LazyRow(contentPadding = PaddingValues(10.dp)) {
                                items(latestState.movie) { movie ->
                                    VerticalMovieCard(
                                        movie = movie.copy(title = "", releaseDate = ""),
                                        checkIsFavorite = {
                                            checkIsFavorite(it)
                                        },
                                        onFavoriteClicked = {
                                            onFavoriteClicked(it)

                                        }
                                    ) {
                                        onMovieClicked(it)
                                    }
                                }
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(color = Color(0xFF282A28))
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Recommended",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 15.dp, top = 15.dp)
                    )
                    when (recommendedState) {
                        is HomeScreenState.GetRecommendedMoviesSuccessState -> {
                            LazyRow(contentPadding = PaddingValues(15.dp)) {
                                println("recommm")
                                items(recommendedState.movie) { movie ->
                                    RecommendedCard(
                                        movie = movie,
                                        checkIsFavorite = {
                                            checkIsFavorite(it)
                                        }
//                                    modifier = Modifier.padding(end = 5.dp),
                                    ) {
                                        onFavoriteClicked(it)
                                    }
                                }
                            }
                        }

                        is HomeScreenState.GetRecommendedMoviesLoadingState -> println("rec loaddd")
                        else -> Unit
                    }
                }
            }
        }
    }
}