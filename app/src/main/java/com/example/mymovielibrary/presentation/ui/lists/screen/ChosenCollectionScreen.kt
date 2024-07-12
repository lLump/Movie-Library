package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.model.events.CollectionEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.model.UiModeType
import com.example.mymovielibrary.presentation.ui.lists.state.CollectionState
import com.example.mymovielibrary.presentation.ui.lists.util.AddMediasDialog
import com.example.mymovielibrary.presentation.ui.lists.util.CollectionMark
import com.example.mymovielibrary.presentation.ui.lists.util.MediaListItem
import com.example.mymovielibrary.presentation.ui.lists.util.SortDialog
import com.example.mymovielibrary.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenCollectionScreen(
    onEvent: (CollectionEvent) -> Unit,
    state: CollectionState,
    collectionId: Int,
    onBackPress: () -> Unit,
    navigateTo: (NavigationRoute) -> Unit
) {

    LaunchedEffect(Unit) {
        onEvent(CollectionEvent.LoadCollection(collectionId))
    }

    var uiMode by remember { mutableStateOf(UiModeType.Default)}

    var isShowAddDialog by remember { mutableStateOf(false) }
    var isShowSortDialog by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }

    var checkedItems by remember { mutableStateOf(listOf<Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (uiMode) {
                            UiModeType.Default -> state.collection.name
                            UiModeType.EditMode -> stringResource(id = R.string.choose_movie)
                            UiModeType.PickingBackgroundImage -> stringResource(id = R.string.choose_movies)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPress ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back button")
                    }
                },
                actions = {
                    when (uiMode) {
                        UiModeType.Default -> {
                            IconButton(onClick = { isShowSortDialog = true }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Sort,
                                    contentDescription = "Edit sortType in collection"
                                )
                            }
                            IconButton(onClick = { uiMode = UiModeType.EditMode }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Collection"
                                )
                            }
                            IconButton(onClick = { expandedMenu = true } ) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Collection Action")
                            }
                            DropdownMenu(
                                expanded = expandedMenu,
                                onDismissRequest = { expandedMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.change_collection_background))},
                                    onClick = {
                                        expandedMenu = false
                                        uiMode = UiModeType.PickingBackgroundImage
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.clear_collection))},
                                    onClick = {
                                        expandedMenu = false

                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.delete_collection))},
                                    onClick = {
                                        expandedMenu = false

                                    }
                                )
                            }
                        }
                        UiModeType.EditMode -> {
                            IconButton(onClick = {
                                onEvent(CollectionEvent.DeleteItems(checkedItems))
                                uiMode = UiModeType.Default
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete from Edit List")
                            }
                            IconButton(onClick = {
                                //сделать по клику вместо PutItems, выбор листа куда добавить выбранные итемы (диалог?)
//                                onEvent(CollectionEvent.PutItemsInList(checkedItems, ListType.COLLECTION))
                                isShowAddDialog = true
                                uiMode = UiModeType.Default
                            }) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.PlaylistAdd, contentDescription = "Add from Edit List")
                            }
                            IconButton(onClick = {
                                uiMode = UiModeType.Default
                                checkedItems = listOf() //clear checked when exit
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Exit from Edit Collection")
                            }
                        }
                        UiModeType.PickingBackgroundImage -> {
                            IconButton(onClick = {
                                uiMode = UiModeType.Default
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Exit from Choosing backdrop_path")
                            }
                        }
                    }
                }
            )
            if (isShowAddDialog) {
                AddMediasDialog(
                    onDismiss = { isShowSortDialog = false },
                    onSuccess = { listType ->
                        onEvent(CollectionEvent.PutItemsInList(checkedItems, listType))
                    }
                )
            }
            if (isShowSortDialog) {
                SortDialog(
                    onDismiss = { isShowSortDialog = false },
                    onSuccess = { sortType ->
                        onEvent(CollectionEvent.UpdateCollectionSortType(sortType))
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            } else {
                CollectionScreenContent(
                    state = state,
                    navigateTo = navigateTo,
                    uiMode = uiMode,
                    itemChecked = { checkedItem ->
                        if (checkedItem in checkedItems) {
                            checkedItems = checkedItems.filter { it != checkedItem }
                        } else {
                            checkedItems = checkedItems + checkedItem
                        }
                    },
                    imageSelected = { imagePath ->
                        onEvent(CollectionEvent.UpdateCollectionBackgroundImage(imagePath))
                        uiMode = UiModeType.Default
                    }
                )
            }
        }
    }
}

@Composable
private fun CollectionScreenContent(
    state: CollectionState,
    navigateTo: (NavigationRoute) -> Unit,
    uiMode: UiModeType = UiModeType.Default,
    itemChecked: (Int) -> Unit,
    imageSelected: (String) -> Unit,
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
            ) {
                rowItems.forEach { media ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(208.dp)
                            .padding(bottom = 8.dp, start = 4.dp, end = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        var itemSelected by remember { mutableStateOf(false) }
                        MediaListItem(
                            mediaItem = media,
                            modifier = Modifier
                                .fillMaxHeight()
                                .shadow(4.dp, RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    when (uiMode) {
                                        UiModeType.Default -> {
                                            navigateTo(NavigationRoute.MediaDetails(media.id))
                                        }

                                        UiModeType.EditMode -> {
                                            itemSelected = !itemSelected
                                            itemChecked(media.id)
                                        }

                                        UiModeType.PickingBackgroundImage -> {
                                            imageSelected(media.backdropPath)
                                        }
                                    }
                                },
                            imageHeight = 130.dp
                        )
                        when (uiMode) {
                            UiModeType.Default -> itemSelected = false
                            UiModeType.EditMode -> {
                                Checkbox(
                                    modifier = Modifier.align(Alignment.TopEnd),
                                    checked = itemSelected,
                                    onCheckedChange = { } //nothing because of logic above. Otherwise bugging often
                                )
                            }
                            UiModeType.PickingBackgroundImage -> { }
                        }
                    }
                }
                // it uses empty space of a list to keep the media from expanding (if in row medias < 3)
                if (rowItems.size < 3) {
                    for (i in rowItems.size until 3) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .height(228.dp)
                        )
                    }
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
//            Text(
//                text = details.name,
//                fontWeight = FontWeight.Bold,
//                style = Typography.titleLarge
//            )
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