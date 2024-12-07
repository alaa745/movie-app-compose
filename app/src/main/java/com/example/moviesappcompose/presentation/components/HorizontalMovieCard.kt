package com.example.moviesappcompose.presentation.components

import android.widget.GridView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesappcompose.R
import com.example.moviesappcompose.data.remote.Result

//@Preview(showBackground = false, showSystemUi = false)
@Composable
fun HorizontalMovieCard(
    movie: Result
) {
    Row(modifier = Modifier.padding(10.dp)) {
        if (movie.posterPath.isNullOrEmpty())
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(170.dp)
                    .clip(shape = RoundedCornerShape(7.dp)),

                contentScale = ContentScale.FillBounds
            )
        else
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(170.dp)
                    .clip(shape = RoundedCornerShape(7.dp)),
                contentScale = ContentScale.FillBounds,
            )
        Column(
            modifier = Modifier
                .height(100.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.releaseDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight,
                color = Color.White
            )
            Text(
                text = movie.voteAverage.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight,
                color = Color.White
            )
        }
    }
}