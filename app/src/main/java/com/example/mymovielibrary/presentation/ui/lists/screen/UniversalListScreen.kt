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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.lists.model.ListType
import com.example.mymovielibrary.domain.model.events.MediaEvent
import com.example.mymovielibrary.presentation.ui.lists.state.ListState
import com.example.mymovielibrary.presentation.ui.lists.util.MediaGridList
import com.example.mymovielibrary.presentation.ui.lists.util.UniversalListScreenInfo

@OptIn(ExperimentalMaterial3Api::class) //TopAppBar
@Composable
fun UniversalListScreen(
    onEvent: (MediaEvent) -> Unit,
    state: ListState,
    screenInfo: UniversalListScreenInfo
) {
    LaunchedEffect(Unit) {
        onEvent(MediaEvent.LoadChosenList(screenInfo.listType))
    }

    var isEditMode by remember { mutableStateOf(false) }
    var checkedItems by remember { mutableStateOf(listOf<Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (screenInfo.listType) {
                            ListType.WATCHLIST -> stringResource(id = R.string.watchlist)
                            ListType.RATED -> stringResource(id = R.string.rated)
                            ListType.FAVORITE -> stringResource(id = R.string.favorite)
                            else -> ""
                        } + " (${state.currentItems})"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = screenInfo.onBackPress) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                },
                actions = {
                    if (isEditMode) {
                        IconButton(onClick = {
                            onEvent(MediaEvent.DeleteItems(checkedItems, screenInfo.listType))
                            isEditMode = false
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete from Edit List")
                        }
                        IconButton(onClick = { onEvent(MediaEvent.EditItems(checkedItems, screenInfo.listType)) }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.PlaylistAdd, contentDescription = "Add from Edit List")
                        }
                        IconButton(onClick = {
                            isEditMode = false
                            checkedItems = listOf() //clear checked when exit
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Exit from Edit List")
                        }
                    } else {
                        IconButton(onClick = { isEditMode = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit List")
                        }
                    }
                }
            )
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
                    navigateTo = screenInfo.navigateTo
                ) { checkedItem ->
                    if (checkedItem in checkedItems) {
                        checkedItems = checkedItems.filter { it != checkedItem }
                    } else {
                        checkedItems = checkedItems + checkedItem
                    }
                }
            }
        }
    }
}