package com.example.mymovielibrary.presentation.ui.lists.state

import com.example.mymovielibrary.domain.account.model.UserCollectionInfo
import com.example.mymovielibrary.domain.lists.model.MediaItem

data class UniversalListState(
    val isLoading: Boolean = true,
    val chosenList: List<MediaItem> = emptyList(),
    val currentItems: Int = 0,
    val userCollections: List<UserCollectionInfo> = emptyList(),
    val checkedMedias: Set<Int> = emptySet()
)