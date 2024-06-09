package com.example.mymovielibrary.presentation.ui.lists.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.theme.Typography

@Composable
fun CollectionMark(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    borderSettings: RoundedCornerShape = RoundedCornerShape(50, 0, 50, 0),
) { //items, average rating, runtime, revenue
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, borderSettings)
            .border(1.25.dp, MaterialTheme.colorScheme.primary, borderSettings)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Icon(
            modifier = Modifier.wrapContentSize(), imageVector = icon, contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = 6.dp)
                .align(Alignment.CenterVertically),
            text = text,
            style = Typography.bodyMedium
        )
    }
}

@Composable
fun MediaListItem(
    mediaItem: MediaItem,
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
//                .aspectRatio(1f / 2.5f)
                .fillMaxSize(),
            model = "https://image.tmdb.org/t/p/original/" + mediaItem.posterPath, //TODO test different sizes
            contentDescription = "MediaListItem"
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(0.5f))
                .fillMaxWidth()
        ) {
            Text(text = mediaItem.title)
            Text(text = mediaItem.date)
        }
    }
}

@Composable
fun MediaGridList(list: List<MediaItem>, navigateTo: (NavigationRoute) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(list) { mediaItem ->
            MediaListItem(
                mediaItem = mediaItem,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navigateTo(NavigationRoute.MediaDetails(mediaItem.id)) }
            )
        }
    }
}