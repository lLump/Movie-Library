package com.example.mymovielibrary.presentation.ui.lists.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.theme.Typography
import java.text.DecimalFormat

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
    imageHeight: Dp,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Crop,
            model = "https://image.tmdb.org/t/p/original/" + mediaItem.posterPath, //TODO test different sizes
            contentDescription = "MediaListItem",
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Star,
                    tint = Color.Yellow,
                    modifier = Modifier
                        .padding(top = 6.dp, start = 6.dp, end = 4.dp)
                        .size(18.dp),
                    contentDescription = null,
                )
                Text(
//                    text = mediaItem.rating.toString() + " (${mediaItem.rateCount})",
                    text = DecimalFormat("#.0").format(mediaItem.rating),
                    modifier = Modifier.padding(top = 6.dp),
                    style = Typography.labelLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = mediaItem.date.substring(0, 4),
                    style = Typography.labelLarge,
                    modifier = Modifier.padding(top = 6.dp, end = 8.dp)
                )
            }
            Text(
                text = mediaItem.title,
                style = Typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 6.dp, end = 6.dp)
            )
        }
    }
}

@Composable
fun MediaGridList(
    list: List<MediaItem>,
    navigateTo: (NavigationRoute) -> Unit,
    isEditMode: Boolean = false,
    itemChecked: (Int) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 0.dp),
    ) {
        items(list) { mediaItem ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(228.dp)
                .padding(bottom = 8.dp, start = 4.dp, end = 4.dp)
                .clip(RoundedCornerShape(12.dp))
            ) {
                var itemSelected by remember { mutableStateOf(false) }

                MediaListItem(
                    mediaItem = mediaItem,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            if (isEditMode) {
                                itemSelected = !itemSelected
                                itemChecked(mediaItem.id)
                            } else {
                                navigateTo(NavigationRoute.MediaDetails(mediaItem.id))
                            }
                        },
                    imageHeight = 150.dp
                )
                if (isEditMode) {
                    Checkbox(
                        modifier = Modifier.align(Alignment.TopEnd),
                        checked = itemSelected,
                        onCheckedChange = { } //nothing because of logic above. Otherwise bugging often
                    )
                } else itemSelected = false
            }
        }
    }
}