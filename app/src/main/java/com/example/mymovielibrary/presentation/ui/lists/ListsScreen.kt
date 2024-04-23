package com.example.mymovielibrary.presentation.ui.lists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.presentation.ui.theme.Typography
import com.example.mymovielibrary.presentation.viewmodel.states.ListState

@Composable
fun ListsScreen(
    onEvent: (ListEvent) -> Unit,
    state: ListState
) {
    LaunchedEffect(Unit) {
        onEvent(ListEvent.LoadCollections)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.collections) { collection ->
            CollectionItem(item = collection)
        }
    }
}

@Composable
fun CollectionItem(item: UserCollection) {
    Card(
        onClick = {}, //TODO load and display films in chosen collection
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(150.dp),
        shape = CardDefaults.elevatedShape
    ) {
        Text(
            text = item.name,
            style = Typography.bodyLarge
        )
        Text(
            text = item.description,
            style = Typography.bodySmall
        )
    }
}