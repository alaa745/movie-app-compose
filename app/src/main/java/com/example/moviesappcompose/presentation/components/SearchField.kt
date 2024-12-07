package com.example.moviesappcompose.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchField(
    onValueChange: (String) -> Unit,
    value: String,
    onSearch: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
//        colors = TextFieldDefaults.colors(),
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedBorderColor = Color(0xFFFFBB3B),
            focusedLeadingIconColor = Color.White,
            unfocusedLeadingIconColor = Color.White
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(value)
            }
        ),
//        shape = OutlinedTextFieldDefaults.shape,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                Modifier.size(23.dp),
            )
        },
        placeholder = {
            Text(
                text = "Search a movie",
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
        }
    )

}