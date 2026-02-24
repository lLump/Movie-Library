package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.domain.model.events.MediaEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.model.UiDialogType
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.AddMediasDialog
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.ConfirmDialog
import com.example.mymovielibrary.presentation.ui.lists.screen.dialogs.ConfirmDialogOptions
import com.example.mymovielibrary.presentation.ui.lists.state.UniversalListState
import com.example.mymovielibrary.presentation.ui.lists.util.MediaGridList
import com.example.mymovielibrary.presentation.ui.util.showToast

@OptIn(ExperimentalMaterial3Api::class) //TopAppBar
@Composable
fun UniversalListScreen(
    onEvent: (MediaEvent) -> Unit,
    state: UniversalListState,
    listType: ListType,
    onBackPress: () -> Unit,
    navigateTo: (NavigationRoute) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(MediaEvent.LoadChosenList(listType))
    }

    var isEditMode by remember { mutableStateOf(false) }
    var currentDialog by remember { mutableStateOf(UiDialogType.NO_DIALOG) }

    val checkedItems = state.checkedMedias
    val context = LocalContext.current //fixme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (listType) {
                            ListType.WATCHLIST -> stringResource(id = R.string.watchlist)
                            ListType.RATED -> stringResource(id = R.string.rated)
                            ListType.FAVORITE -> stringResource(id = R.string.favorite)
                            ListType.COLLECTION -> "Impossible scenario"
                        } + " (${state.currentItems})"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                },
                actions = {
                    if (isEditMode) {
                        IconButton(onClick = {
                            if (checkedItems.isEmpty()) {
                                showToast(context, R.string.need_to_choose)
                            } else {
//                                confirmDialogOptions = ConfirmDialogOptions.DELETE_MEDIAS
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
                                isEditMode = false
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                                contentDescription = "Add from Edit List"
                            )
                        }
                        IconButton(onClick = {
                            isEditMode = false
                            onEvent(MediaEvent.ClearMediaChecks)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Exit from Edit List"
                            )
                        }
                    } else {
                        IconButton(onClick = { isEditMode = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit List")
                        }
                    }
                }
            )
            when(currentDialog) {
                UiDialogType.ADD_DIALOG -> {
                    val toastText = "${checkedItems.count()} " + context.getString(R.string.media_items) + " " + context.getString(R.string.successful_added)
                    AddMediasDialog(
                        userCollections = state.userCollections,
                        currentList = listType,
                        onDismiss = { currentDialog = UiDialogType.NO_DIALOG },
                        onListChosen = { listType ->
                            onEvent(MediaEvent.PutItemsInList(checkedItems, listType))
                            showToast(context, toastText)
                        },
                        onCollectionChosen = { collectionId ->
                            onEvent(MediaEvent.PutItemsInCollection(checkedItems, collectionId))
                            showToast(context, toastText)
                        }
                    )
                }
                UiDialogType.CONFIRM_DIALOG -> ConfirmDialog(
                    onDismiss = { currentDialog = UiDialogType.NO_DIALOG },
                    onSuccess = {
//                        if (confirmDialogOptions == ConfirmDialogOptions.DELETE_MEDIAS) {
                            onEvent(MediaEvent.DeleteItems(checkedItems, listType))
                            showToast(
                                context = context,
                                text = "${checkedItems.count()} " + context.getString(R.string.media_items) + " " + context.getString(
                                    R.string.successful_deleted
                                )
                            )
                            isEditMode = false
                            onEvent(MediaEvent.ClearMediaChecks)
//                        }
                    },
//                    dialogOptions = confirmDialogOptions,
                    dialogOptions = ConfirmDialogOptions.DELETE_MEDIAS,
                    itemCount = checkedItems.count()
                )
                UiDialogType.EDIT_DIALOG -> {}
                UiDialogType.SORT_DIALOG -> {}
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
                MediaGridList(
                    list = state.chosenList,
                    isEditMode = isEditMode,
                    checkedMedias = checkedItems,
                    navigateTo = navigateTo
                ) { checkedItem ->
                    onEvent(MediaEvent.ToggleMediaCheck(checkedItem))
                }
            }
        }
    }
}