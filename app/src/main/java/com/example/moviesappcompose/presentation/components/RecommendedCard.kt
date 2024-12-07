package com.example.moviesappcompose.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviesappcompose.R
import com.example.moviesappcompose.domain.Movie

@Composable
fun RecommendedCard(
    movie: Movie,
    height: Int = 145,
    width: Int = 115,
    checkIsFavorite: (Movie) -> Boolean,
    onFavoriteClicked: (Movie) -> Unit,
) {
    Column(Modifier.padding(end = 10.dp).background(Color(0xFF343534))) {

        Box(contentAlignment = Alignment.TopStart) {
            Card(shape = RoundedCornerShape(topStart = 6.dp , topEnd = 6.dp)) {
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
        Row(
            modifier = Modifier.padding(top = 5.dp , start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.review_icon),
                contentDescription = null,
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            )
            Text(
                text = movie.voteAverage.toString(),
                fontSize = 13.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Text(
            text = movie.title,
            modifier = Modifier.width(115.dp).padding(start = 5.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = movie.releaseDate,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 5.dp),

            fontWeight = FontWeight.Light,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

    }
}