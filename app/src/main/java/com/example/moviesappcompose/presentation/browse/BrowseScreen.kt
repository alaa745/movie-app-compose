package com.example.moviesappcompose.presentation.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesappcompose.presentation.components.CategoryCard

@Composable
fun BrowseScreen(
    onCategoryClicked: (Int) -> Unit
) {
    val viewmodel: BrowseScreenViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .background(Color(0xFF121312))
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Text(
            text = "Browse Categories",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 16.dp, top = 15.dp)// Space below the text
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), contentPadding = PaddingValues(10.dp), modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(viewmodel.moviesCategoriesList.value) { genre ->
                CategoryCard(categoryName = genre.name, genreId = genre.id) {
                    onCategoryClicked(it)
                }
            }
        }
    }
}