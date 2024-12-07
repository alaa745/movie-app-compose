package com.example.moviesappcompose.presentation.movieDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.moviesappcompose.R
import com.example.moviesappcompose.domain.MovieDetails
import com.example.moviesappcompose.presentation.components.RecommendedCard
import com.example.moviesappcompose.presentation.components.VerticalMovieDetailsCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int
) {
    val viewModel: MovieDetailsScreenViewModel = hiltViewModel()
    viewModel.getMovieDetails(movieId)
    val movieDetailsScreenStateState = viewModel.movieDetailsState.observeAsState()
    val recommendedMoviesState = viewModel.movieRecommendationsState.observeAsState()
    LazyColumn(
        modifier = Modifier
            .background(Color(0xFF121312))
            .fillMaxSize()
    ) {
        item {
            when (movieDetailsScreenStateState.value) {
                is MovieDetailsScreenState.GetMovieDetailsSuccessState -> {
                    Column {
                        if ((movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.backdrop_path
                                .isEmpty()
                        )
                            Image(
                                painter = painterResource(id = R.drawable.placeholder),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.height(220.dp)
                            )
                        else
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${(movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.backdrop_path}",
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.height(220.dp)
                            )

                        Text(
                            text = (movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.padding(top = 15.dp, start = 13.dp)
                        )
                        Text(
                            text = (movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.release_date,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light,
                            color = Color(0xFFB5B4B4),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.padding(top = 5.dp, start = 13.dp)
                        )
                        Row(Modifier.padding(top = 15.dp)) {
                            VerticalMovieDetailsCard(
                                movie = (movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie,
                                height = 250,
                                width = 150,
                                modifier = Modifier.padding(top = 5.dp, start = 13.dp),
                                checkIsFavorite = {
                                    viewModel.checkIsFavorite(movieId = it.id)
                                },
                                onFavoriteClicked = {
                                    viewModel.toggleFavoriteMovie(movie = it)
                                }
                            ) {

                            }
                            Column(
                                Modifier
                                    .padding(start = 12.dp)
                                    .height(250.dp), verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                FlowRow {
                                    (movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.genres?.forEach { genre ->
                                        Card(
                                            shape = RoundedCornerShape(7.dp),
                                            border = BorderStroke(
                                                width = 2.dp,
                                                color = Color(0xFF514F4F)
                                            ),
                                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .size(width = 80.dp, height = 33.dp)
                                        ) {
                                            Text(
                                                text = genre.name,
                                                color = Color(0xFFCBCBCB),
                                                fontSize = 12.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .align(Alignment.CenterHorizontally)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = (movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.overview,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFB5B4B4),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 5,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                                Row(
                                    modifier = Modifier.padding(top = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.review_icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(20.dp)
                                            .width(20.dp)
                                    )
                                    Text(
                                        text = (movieDetailsScreenStateState.value as MovieDetailsScreenState.GetMovieDetailsSuccessState).movie.vote_average.toString(),
                                        fontSize = 17.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 10.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                is MovieDetailsScreenState.GetMovieDetailsLoadingState -> CircularProgressIndicator()
                else -> Unit
            }
        }
        item {
            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .background(color = Color(0xFF282A28))
                    .fillMaxWidth()
            ) {

                Column {
                    Text(
                        text = "More Like This",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 15.dp, top = 15.dp)
                    )
                    when (recommendedMoviesState.value) {
                        is MovieDetailsScreenState.GetMovieDetailsRecommendationsSuccessState -> {
                            LazyRow(contentPadding = PaddingValues(15.dp)) {
                                items((recommendedMoviesState.value as MovieDetailsScreenState.GetMovieDetailsRecommendationsSuccessState).movies) { movie ->
                                    RecommendedCard(
                                        movie = movie,
                                        checkIsFavorite = {
                                            viewModel.checkIsFavorite(movieId = it.id)
                                        }
//                                    modifier = Modifier.padding(end = 5.dp),
                                    ) {
                                        viewModel.toggleFavoriteMovie(
                                            movie = MovieDetails(
                                                id = movie.id,
                                                adult = movie.adult,
                                                backdrop_path = movie.backdropPath ?: "",
                                                popularity = movie.popularity,
                                                poster_path = movie.posterPath,
                                                overview = movie.overview,
                                                release_date = movie.releaseDate,
                                                title = movie.title,
                                                original_language = movie.originalLanguage,
                                                original_title = movie.originalTitle,
                                                video = movie.video,
                                                vote_count = movie.voteCount,
                                                vote_average = movie.voteAverage,
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        is MovieDetailsScreenState.GetMovieDetailsRecommendationsLoadingState -> println(
                            "rec loaddd"
                        )

                        else -> Unit
                    }
                }
            }
        }
    }
}