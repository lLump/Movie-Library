package com.example.mymovielibrary.presentation.ui.lists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.mymovielibrary.domain.model.events.ListEvent

@Composable
fun ListsScreen(
    onEvent: (ListEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(ListEvent.LoadCollections)
    }

}