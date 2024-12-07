package com.example.moviesappcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesappcompose.R

@Composable
fun CategoryCard(
    categoryName: String,
    genreId: Int,
    onCategoryClicked: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)).clickable {
            onCategoryClicked(genreId)
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.category_card),
            contentDescription = null,
            modifier = Modifier
                .height(110.dp)
                .width(200.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            contentScale = ContentScale.FillBounds,
        )
        Text(text = categoryName, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}