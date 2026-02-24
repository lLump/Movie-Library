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
import androidx.compose.foundation.layout.isImeVisible
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.image.BackdropSize
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.model.events.CollectionEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.model.UiDialogType
import com.example.mymovielibrary.presentation.ui.lists.model.UiModeType
import com.example.mymovielibrary.presentation.ui.lists.screen.additional.BackdropChooseScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.AddMediasDialog
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.CollectionDialog
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.ConfirmDialog
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.ConfirmDialogOptions
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.SortDialog
import com.example.mymovielibrary.presentation.ui.lists.state.CollectionState
import com.example.mymovielibrary.presentation.ui.lists.util.CollectionMark
import com.example.mymovielibrary.presentation.ui.lists.util.MediaListItem
import com.example.mymovielibrary.presentation.ui.theme.Typography
import com.example.mymovielibrary.presentation.ui.util.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenCollectionScreen(
    onEvent: (CollectionEvent) -> Unit,
    state: CollectionState,
    collectionId: Int,
    onBackPress: () -> Unit,
    navigateTo: (NavigationRoute) -> Unit,
) {

    LaunchedEffect(Unit) {
        onEvent(CollectionEvent.LoadCollection(collectionId))
    }

    var uiMode by remember { mutableStateOf(UiModeType.DEFAULT) }
    var currentDialog by remember { mutableStateOf(UiDialogType.NO_DIALOG) }
    var confirmDialogOptions by remember { mutableStateOf(ConfirmDialogOptions.DEFAULT)}

    var expandedMenu by remember { mutableStateOf(false) }
    val checkedItems = state.checkedMedias

    val context = LocalContext.current //fixme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (uiMode) {
                            UiModeType.DEFAULT -> state.collection.name
                            UiModeType.EDIT_MODE -> stringResource(id = R.string.choose_movies)
                            UiModeType.PICKING_BACKDROP -> stringResource(id = R.string.choose_poster)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = if (uiMode == UiModeType.PICKING_BACKDROP) {
                            { uiMode = UiModeType.DEFAULT }
                        } else onBackPress
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                },
                actions = {
                    when (uiMode) {
                        UiModeType.DEFAULT -> {
                            IconButton(onClick = { currentDialog = UiDialogType.SORT_DIALOG }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Sort,
                                    contentDescription = "Edit sortType in collection"
                                )
                            }
                            IconButton(onClick = { uiMode = UiModeType.EDIT_MODE }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Collection"
                                )
                            }
                            IconButton(onClick = { expandedMenu = true }) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "Collection Action"
                                )
                            }
                            DropdownMenu(
                                expanded = expandedMenu,
                                onDismissRequest = { expandedMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.edit_collection)) },
                                    onClick = {
                                        expandedMenu = false
                                        currentDialog = UiDialogType.EDIT_DIALOG
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.change_collection_background)) },
                                    onClick = {
                                        expandedMenu = false
                                        uiMode = UiModeType.PICKING_BACKDROP
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.clear_collection)) },
                                    onClick = {
                                        expandedMenu = false
                                        confirmDialogOptions = ConfirmDialogOptions.CLEAR_COLLECTION
                                        currentDialog = UiDialogType.CONFIRM_DIALOG
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.delete_collection)) },
                                    onClick = {
                                        expandedMenu = false
                                        confirmDialogOptions = ConfirmDialogOptions.DELETE_COLLECTION
                                        currentDialog = UiDialogType.CONFIRM_DIALOG
                                    }
                                )
                            }
                        }

                        UiModeType.EDIT_MODE -> {
                            IconButton(onClick = {
                                if (checkedItems.isEmpty()) {
                                    showToast(context, R.string.need_to_choose)
                                } else {
                                    confirmDialogOptions = ConfirmDialogOptions.DELETE_MEDIAS
                                    currentDialog = UiDialogType.CONFIRM_DIALOG
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete from Edit List"
                                )
                            }
                            IconButton(onClick = {
                                if (checkedItems.isEmpty()) {
                                    showToast(context, R.string.need_to_choose)
                                } else {
                                    currentDialog = UiDialogType.ADD_DIALOG
                                    uiMode = UiModeType.DEFAULT
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                                    contentDescription = "Add from Edit List"
                                )
                            }
                            IconButton(onClick = {
                                uiMode = UiModeType.DEFAULT
                                onEvent(CollectionEvent.ClearMediaChecks) //clear checked when exit
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Exit from Edit Collection"
                                )
                            }
                        }

                        UiModeType.PICKING_BACKDROP -> {
                            IconButton(onClick = {
                                uiMode = UiModeType.DEFAULT
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Exit from Choosing backdrop_path"
                                )
                            }
                        }
                    }
                }
            )
            when (currentDialog) {
                UiDialogType.ADD_DIALOG -> {
                    val toastText = "${checkedItems.count()} " + stringResource(R.string.media_items) + " " + stringResource(R.string.successful_added)

                    AddMediasDialog(
                        userCollections = state.userCollections,
                        currentCollectionId = collectionId,
                        onDismiss = {
                            onEvent(CollectionEvent.ClearMediaChecks)
                            currentDialog = UiDialogType.NO_DIALOG
                        },
                        onListChosen = { listType ->
                            onEvent(CollectionEvent.PutItemsInList(checkedItems, listType))
                            showToast(context, toastText)
                            onEvent(CollectionEvent.ClearMediaChecks)
                        },
                        onCollectionChosen = { collectionId ->
                            onEvent(CollectionEvent.PutItemsInCollection(checkedItems,collectionId))
                            showToast(context, toastText)
                            onEvent(CollectionEvent.ClearMediaChecks)
                        }
                    )
                }

                UiDialogType.SORT_DIALOG -> SortDialog(
                    onDismiss = { currentDialog = UiDialogType.NO_DIALOG },
                    onSuccess = { sortType ->
                        onEvent(CollectionEvent.UpdateCollectionSortType(sortType))
                    }
                )

                UiDialogType.EDIT_DIALOG -> CollectionDialog(
                    collectionName = state.collection.name,
                    collectionDescription = state.collection.description,
                    isPublic = state.collection.public,
                    onDismiss = { currentDialog = UiDialogType.NO_DIALOG },
                    onSuccess = { name, description, isPublic ->
                        onEvent(CollectionEvent.UpdateCollection(name, description, isPublic))
                    }
                )
                UiDialogType.CONFIRM_DIALOG -> {
                    ConfirmDialog(
                        onDismiss = { currentDialog = UiDialogType.NO_DIALOG },
                        onSuccess = {
                            when (confirmDialogOptions) {
                                ConfirmDialogOptions.DELETE_COLLECTION -> {
                                    onEvent(CollectionEvent.DeleteCollection(collectionId))
                                    showToast(context, R.string.collection_deleted)
                                    onBackPress()
                                }

                                ConfirmDialogOptions.CLEAR_COLLECTION -> {
                                    onEvent(CollectionEvent.ClearCollection(collectionId))
                                    showToast(context, R.string.collection_cleared)
                                }

                                ConfirmDialogOptions.DELETE_MEDIAS -> {
                                    onEvent(CollectionEvent.DeleteItems(checkedItems))
                                    showToast(
                                        context = context,
                                        text = "${checkedItems.count()} " + context.getString(R.string.media_items) + " " + context.getString(R.string.successful_deleted))
                                    uiMode = UiModeType.DEFAULT
                                    onEvent(CollectionEvent.ClearMediaChecks)
                                }

                                ConfirmDialogOptions.DEFAULT -> {}
                            }
                        },
                        dialogOptions = confirmDialogOptions,
                        itemCount = checkedItems.count()
                    )
                }
                UiDialogType.NO_DIALOG -> {}
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
                if (uiMode == UiModeType.PICKING_BACKDROP) {
                    BackdropChooseScreen(
                        urls = state.collection.movies.map { it.backdropPath },
                        onChoose = { imageUrl ->
                            onEvent(CollectionEvent.UpdateCollectionBackgroundImage(imageUrl))
                            uiMode = UiModeType.DEFAULT
                        }
                    )
                } else {
                    CollectionScreenContent(
                        state = state,
                        navigateTo = navigateTo,
                        uiMode = uiMode,
                        itemChecked = { checkedItemIndex ->
                            onEvent(CollectionEvent.ToggleMediaCheck(checkedItemIndex))
                        },
                        imageSelected = { imagePath ->
                            onEvent(CollectionEvent.UpdateCollectionBackgroundImage(imagePath))
                            uiMode = UiModeType.DEFAULT
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CollectionScreenContent(
    state: CollectionState,
    navigateTo: (NavigationRoute) -> Unit,
    uiMode: UiModeType = UiModeType.DEFAULT,
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
        items(
            items = state.collection.movies.chunked(3)
        ) { rowItems ->
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
                        val isChecked = media.id in state.checkedMedias
                        MediaListItem(
                            mediaItem = media,
                            modifier = Modifier
                                .fillMaxHeight()
                                .shadow(4.dp, RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    when (uiMode) {
                                        UiModeType.DEFAULT -> {
                                            navigateTo(NavigationRoute.MediaDetails(media.id))
                                        }

                                        UiModeType.EDIT_MODE -> {
                                            itemChecked(media.id)
                                        }

                                        UiModeType.PICKING_BACKDROP -> {
                                            imageSelected(media.backdropPath)
                                        }
                                    }
                                },
                            imageHeight = 130.dp
                        )
                        when (uiMode) {
                            UiModeType.DEFAULT -> {
//                                isChecked = false
                            }
                            UiModeType.EDIT_MODE -> {
                                Checkbox(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 4.dp, end = 4.dp),
                                    checked = isChecked,
                                    onCheckedChange = null
                                )
                            }

                            UiModeType.PICKING_BACKDROP -> {}
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
            model = BackdropSize.ORIGINAL.url + details.backdropPath,
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