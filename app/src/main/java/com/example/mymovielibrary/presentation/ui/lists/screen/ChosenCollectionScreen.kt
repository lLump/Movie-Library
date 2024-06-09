package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.state.CollectionState
import com.example.mymovielibrary.presentation.ui.lists.util.CollectionMark
import com.example.mymovielibrary.presentation.ui.lists.util.MediaListItem
import com.example.mymovielibrary.presentation.ui.theme.Typography

@Composable
fun ChosenCollectionScreen(
    onEvent: (ListEvent) -> Unit,
    state: CollectionState,
    collectionId: Int,
    navigateTo: (NavigationRoute) -> Unit,
) {

    LaunchedEffect(Unit) {
        onEvent(ListEvent.LoadChosenCollection(collectionId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
        } else {
            CollectionScreenContent(state, navigateTo)
        }
    }
}

@Composable
private fun CollectionScreenContent(
    state: CollectionState,
    navigateTo: (NavigationRoute) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Collection(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    ),
                details = state.collection,
            )
        }
        items(state.collection.movies.chunked(3)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (media in rowItems) {
                    MediaListItem(
                        mediaItem = media,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .clickable { navigateTo(NavigationRoute.MediaDetails(media.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Collection(modifier: Modifier, details: CollectionDetails) {
    Box(modifier = modifier) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/" + details.backdropPath,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
//            clipToBounds = true,
            contentScale = ContentScale.Crop,
            contentDescription = "Background collection photo",
        )
        Column(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.5f))
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 6.dp)
        ) {
            Text(
                text = details.name,
                fontWeight = FontWeight.Bold,
                style = Typography.titleLarge
            )
            Text(
                text = details.description,
                fontWeight = FontWeight.SemiBold,
                style = Typography.bodyMedium
            )
        }
        CollectionMark(
            modifier = Modifier
                .align(Alignment.TopEnd),
//                    .align(Alignment.CenterEnd)
//                    .padding(top = 18.dp),
            borderSettings = RoundedCornerShape(topEnd = 11.dp, bottomStart = 12.dp),
//                borderSettings = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
            text = details.revenue,
            icon = Icons.Default.AttachMoney
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
//                    .background(Color.Black.copy(alpha = 0.5f))
                .fillMaxWidth()
        ) {
            CollectionMark(
//                    text = "Items: ${item.itemsCount}",
                text = details.itemsCount,
                icon = Icons.Default.Movie,
                borderSettings = RoundedCornerShape(50, 0, 50, 0),
            )
            CollectionMark(
//                    text = "Rating: ${item.averageRating}",
                text = details.averageRating,
                icon = Icons.Default.Star,
                borderSettings = RoundedCornerShape(50, 0, 50, 0),
            )
            CollectionMark(
//                    text = "Time: ${item.runtime.first}h ${item.runtime.second}m",
                text = "${details.runtime.first}h ${details.runtime.second}m",
                icon = Icons.Default.AccessTime,
                borderSettings = RoundedCornerShape(50, 0, 50, 0),
            )
        }
    }
}