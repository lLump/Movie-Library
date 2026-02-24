package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.image.BackdropSize
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.CollectionDialog
import com.example.mymovielibrary.presentation.ui.lists.state.UserListsState
import com.example.mymovielibrary.presentation.ui.lists.util.CollectionMark
import com.example.mymovielibrary.presentation.ui.lists.util.MediaListItem
import com.example.mymovielibrary.presentation.ui.theme.Typography

@Composable
fun ListsScreen(
    onEvent: (ListEvent) -> Unit,
    state: UserListsState,
    navigateTo: (NavigationRoute) -> Unit,
    paddingValues: PaddingValues,
) {
    LaunchedEffect(Unit) {
        onEvent(ListEvent.LoadScreen) //fixme delete
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            ListsScreenContent(state, onEvent, navigateTo)
        }
    }
}

@Composable
private fun ListsScreenContent(
    state: UserListsState,
    onEvent: (ListEvent) -> Unit,
    navigateTo: (NavigationRoute) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CollectionsRowList(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
//                .align(Alignment.CenterHorizontally)
//                .weight(0.3f)
                .padding(bottom = 16.dp)
                .background(
                    MaterialTheme.colorScheme.onBackground.copy(0.5f),
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                ),
            collections = state.userCollections,
            onEvent = onEvent,
            navigateTo = navigateTo,
        )
        MediaRowList(
            listTitleId = R.string.watchlist,
            list = state.watchlist,
            navigateTo = navigateTo,
            route = NavigationRoute.UniversalList(ListType.WATCHLIST.route)
        )
        MediaRowList(
            listTitleId = R.string.rated,
            list = state.rated,
            navigateTo = navigateTo,
            route = NavigationRoute.UniversalList(ListType.RATED.route)
        )
        MediaRowList(
            listTitleId = R.string.favorite,
            list = state.favorite,
            navigateTo = navigateTo,
            route = NavigationRoute.UniversalList(ListType.FAVORITE.route)
        )
    }
}

@Composable
private fun MediaRowList(
    @StringRes listTitleId: Int,
    list: List<MediaItem>,
    navigateTo: (NavigationRoute) -> Unit,
    route: NavigationRoute,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(350.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(12.dp)),
    ) {
        TitleOfList(
            modifier = Modifier
                .weight(0.15f)
                .padding(start = 16.dp, top = 8.dp),
            textId = listTitleId
        ) {
            navigateTo(route)
        }
        LazyRow(
            modifier = Modifier.weight(0.85f),
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { mediaItem ->
                MediaListItem(
                    mediaItem = mediaItem,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(135.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp)) // без этого, эффект нажатия квадратный
                        .clickable { navigateTo(NavigationRoute.MediaDetails(mediaItem.id)) },
                    imageHeight = 200.dp
                )
            }
        }
    }
}

@Composable
private fun TitleOfList(
    modifier: Modifier,
    @StringRes textId: Int,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(stringResource(id = textId))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Redirect to chosen list"
            )
        }
    }
}

@Composable
private fun CollectionsRowList(
    modifier: Modifier,
    collections: List<UserCollection>,
    navigateTo: (NavigationRoute) -> Unit,
    onEvent: (ListEvent) -> Unit,
) {
    var isShowCreateDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 26.dp)
                    .align(Alignment.CenterStart),
                text = stringResource(id = R.string.collections),
                style = Typography.titleLarge,
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(collections) { collection ->
                CollectionItemList(item = collection) {
                    navigateTo(NavigationRoute.CollectionDetails(collection.id))
                }
            }
            item {
                CreateCollectionItemList {
                    isShowCreateDialog = true
                }
            }
        }
    }
    if (isShowCreateDialog) {
        CollectionDialog(
            isCreating = true,
            onDismiss = { isShowCreateDialog = false },
            onSuccess = { name, description, isPublic ->
                onEvent(ListEvent.CreateCollection(name, description, isPublic))
            }
        )
    }
}

@Composable
private fun CollectionItemList(item: UserCollection, chosenCollection: () -> Unit) {
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
                model = BackdropSize.W780.url + item.backdropPath,
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
                modifier = Modifier
                    .align(Alignment.TopEnd),
//                    .align(Alignment.CenterEnd)
//                    .padding(top = 18.dp),
                borderSettings = RoundedCornerShape(topEnd = 11.dp, bottomStart = 12.dp),
//                borderSettings = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
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
private fun CreateCollectionItemList(onCreate: () -> Unit) {
    Card(
        onClick = onCreate,
        modifier = Modifier
            .aspectRatio(2f / 1.1f)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxHeight(),
        shape = CardDefaults.elevatedShape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxSize(0.4f)
                    .align(Alignment.Center),
                imageVector = Icons.Default.AddBox,
                contentDescription = "Collection creating"
            )
        }
    }
}