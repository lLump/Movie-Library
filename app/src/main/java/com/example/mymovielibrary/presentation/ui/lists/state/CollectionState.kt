package com.example.mymovielibrary.presentation.ui.lists.state

import com.example.mymovielibrary.domain.lists.model.CollectionDetails

data class CollectionState(
    val isLoading: Boolean = true,
    val collection: CollectionDetails = CollectionDetails(
        id = 0,
        name = "",
        description = "",
        movies = listOf(),
        averageRating = "",
        runtime = Pair("", ""),
        revenue = "",
        backdropPath = null,
        itemsCount = "",
        public = true
    ),
    val checkedMedias: Set<Int> = emptySet()
)
