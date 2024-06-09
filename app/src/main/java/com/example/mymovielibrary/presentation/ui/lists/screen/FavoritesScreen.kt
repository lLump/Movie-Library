package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.state.ListState
import com.example.mymovielibrary.presentation.ui.lists.util.MediaGridList

@Composable
fun FavoritesScreen(
    onEvent: (ListEvent) -> Unit,
    state: ListState,
    navigateTo: (NavigationRoute) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(ListEvent.LoadFavorites)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
        } else {
            MediaGridList(state.favorite, navigateTo)
        }
    }
}
