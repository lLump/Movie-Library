package com.example.mymovielibrary.presentation.ui.lists.screen.additional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.domain.image.BackdropSize

@Composable
fun BackdropChooseScreen(urls: List<String>, onChoose: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp),
    ) {
        items(urls) { imageUrl ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp, end = 4.dp)
                    .clickable {
                        onChoose(imageUrl)
                    },
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = BackdropSize.W1280.url + imageUrl,
                    contentDescription = "Backdrop choose",
                )
            }
        }
    }
}