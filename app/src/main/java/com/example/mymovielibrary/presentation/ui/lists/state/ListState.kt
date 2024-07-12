package com.example.mymovielibrary.presentation.ui.lists.state

import com.example.mymovielibrary.domain.lists.model.MediaItem

data class ListState(
    val isLoading: Boolean = true,
    val chosenList: List<MediaItem> = emptyList(),
    val currentItems: Int = 0,
)