package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.presentation.ui.theme.Typography

@Composable
fun CollectionItem(item: UserCollection, chosenCollection: () -> Unit) {
    Card(
        onClick = chosenCollection,
        modifier = Modifier
            .aspectRatio(2f / 1.1f)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxHeight(),
        shape = CardDefaults.elevatedShape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/" + item.backdropPath,
                modifier = Modifier.fillMaxSize(),
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
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    style = Typography.titleLarge
                )
                Text(
                    text = item.description,
                    fontWeight = FontWeight.SemiBold,
                    style = Typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.updated) + item.updatedAt,
                    style = Typography.bodySmall
                )
            }
            CollectionMark(
                modifier = Modifier.align(Alignment.TopEnd),
                borderSettings = RoundedCornerShape(topEnd = 11.dp, bottomStart = 12.dp),
                text = item.revenue,
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
                    text = item.itemsCount,
                    icon = Icons.Default.Movie,
                    borderSettings = RoundedCornerShape(50, 0, 50, 0),
                )
                CollectionMark(
//                    text = "Rating: ${item.averageRating}",
                    text = item.averageRating,
                    icon = Icons.Default.Star,
                    borderSettings = RoundedCornerShape(50, 0, 50, 0),
                )
                CollectionMark(
//                    text = "Time: ${item.runtime.first}h ${item.runtime.second}m",
                    text = "${item.runtime.first}h ${item.runtime.second}m",
                    icon = Icons.Default.AccessTime,
                    borderSettings = RoundedCornerShape(50, 0, 50, 0),
                )
            }
        }
    }
}

@Composable
private fun CollectionMark(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    borderSettings: RoundedCornerShape = RoundedCornerShape(50, 0, 50, 0),
) { //items, avg rating, runtime, revenue
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, borderSettings)
            .border(1.25.dp, MaterialTheme.colorScheme.primary, borderSettings)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Icon(
            modifier = Modifier.wrapContentSize(),
            imageVector = icon,
            contentDescription = null
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