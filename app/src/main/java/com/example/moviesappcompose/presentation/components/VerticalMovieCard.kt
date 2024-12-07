package com.example.moviesappcompose.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesappcompose.R
import com.example.moviesappcompose.data.remote.Result
import com.example.moviesappcompose.domain.Movie
import com.example.moviesappcompose.presentation.home.HomeScreenViewModel


@Composable
fun VerticalMovieCard(
    movie: Movie,
    height: Int = 160,
    width: Int = 115,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    checkIsFavorite: (Movie) -> Boolean,
    onFavoriteClicked: (Movie) -> Unit,
    onMovieClicked: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Box(contentAlignment = Alignment.TopStart, modifier = modifier) {
            Card(shape = RoundedCornerShape(6.dp) , modifier = Modifier.clickable {
                onMovieClicked(movie.id)
            }) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = null,
                    modifier = Modifier
                        .height(height.dp)
                        .width(width.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            Image(
                painter = painterResource(id = if (checkIsFavorite(movie)) R.drawable.saved_icon else R.drawable.bookmark_icon),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(30.dp)
                    .clip(RoundedCornerShape(topStart = 6.dp))
                    .clickable {
                        onFavoriteClicked(movie)
                    }
            )
        }
        Column(
            Modifier
                .height(70.dp)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
//                modifier = Modifier.padding(bottom = 40.dp)
            )
            Text(
                text = movie.releaseDate,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
//                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}