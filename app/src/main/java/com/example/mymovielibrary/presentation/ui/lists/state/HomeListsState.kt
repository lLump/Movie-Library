package com.example.mymovielibrary.presentation.ui.lists.state

import com.example.mymovielibrary.domain.lists.model.MediaItem

data class HomeListsState(
    val trending: List<MediaItem> = emptyList(),
    val nowPlaying: List<MediaItem> = emptyList(),
    val popular: List<MediaItem> = emptyList(),
    val topRated: List<MediaItem> = emptyList(),
    val upcoming: List<MediaItem> = emptyList(), //tvShows only
    val recentlyReleased: List<MediaItem> = emptyList(), //films only
)