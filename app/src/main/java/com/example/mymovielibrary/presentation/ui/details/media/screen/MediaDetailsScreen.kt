package com.example.mymovielibrary.presentation.ui.details.media.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.mymovielibrary.domain.model.events.MediaDetailsEvent
import com.example.mymovielibrary.presentation.ui.details.media.state.MediaDetailsState

@Composable
fun MediaDetailsScreen(
    onEvent: (MediaDetailsEvent) -> Unit,
    onBackPress: () -> Unit,
    state: MediaDetailsState,
) {
    LaunchedEffect(Unit) {
        onEvent(MediaDetailsEvent.LoadDetails)
    }
}