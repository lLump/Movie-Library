package com.example.mymovielibrary.presentation.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BottomTrending(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onMovieClick(it) }
            )
        }

        item {
            MoviesSection(
                title = "Trending Today",
                onMovieClick = onMovieClick
            )
        }

        item {
            MoviesSection(
                title = "Popular Movies",
                onMovieClick = onMovieClick
            )
        }

        item {
            MoviesSection(
                title = "Popular TV Shows",
                onMovieClick = onMovieClick
            )
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun BottomTrending(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(0) } // movieId
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = "FEATURED BACKDROP",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Movie Title",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "⭐ 8.7",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun MoviesSection(
    title: String,
    onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(10) { index ->
                MoviePoster(
                    onClick = { onMovieClick(index) }
                )
            }
        }
    }
}

@Composable
private fun MoviePoster(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "Poster",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}