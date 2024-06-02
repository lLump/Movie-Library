package com.example.mymovielibrary.presentation.ui.lists.state

import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.UserCollection

data class ListState (
    val isLoading: Boolean = true,
    val userCollections: List<UserCollection> = emptyList(),
    val watchlist: List<MediaItem> = emptyList(),
    val rated: List<MediaItem> = emptyList(),
    val favorite: List<MediaItem> = emptyList(),
)