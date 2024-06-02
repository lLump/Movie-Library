package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.presentation.ui.lists.viewModel.ListViewModel

@Composable
fun UserCollectionsScreen(
    viewModel: ListViewModel = hiltViewModel(),
    collectionId: Int,
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(ListEvent.LoadChosenCollection(collectionId))
    }
}