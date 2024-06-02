package com.example.mymovielibrary.presentation.ui.lists.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.lists.state.ListState
import com.example.mymovielibrary.presentation.ui.lists.viewModel.ListViewModel

@Composable
fun ListsScreen(
    viewModel: ListViewModel = hiltViewModel(),
    padding: PaddingValues,
    toScreen: (NavigationRoute) -> Unit,
) {
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ListEvent.LoadScreen)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
        } else {
            ScreenContent(state, viewModel::onEvent, toScreen)
        }
    }
}

@Composable
private fun ScreenContent(
    state: ListState,
    onEvent: (ListEvent) -> Unit,
    toScreen: (NavigationRoute) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CollectionsRowList(
            modifier = Modifier
                .fillMaxSize()
//                .align(Alignment.CenterHorizontally)
                .weight(0.3f)
                .padding(16.dp)
                .background(Color.Green, RoundedCornerShape(12.dp)),
            collections = state.userCollections,
            toScreen = toScreen,
            onEvent = onEvent
        )
        Row(modifier = Modifier.weight(0.7f)) {

        }
    }
}

@Composable
private fun CollectionsRowList(
    modifier: Modifier,
    collections: List<UserCollection>,
    onEvent: (ListEvent) -> Unit,
    toScreen: (NavigationRoute) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(collections) { collection ->
            CollectionItem(item = collection) {
                onEvent(ListEvent.LoadChosenCollection(collection.id))
                toScreen(NavigationRoute.CollectionDetails(collection.id))
            }
        }
    }
}