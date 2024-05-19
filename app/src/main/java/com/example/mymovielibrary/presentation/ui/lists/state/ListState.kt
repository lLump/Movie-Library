package com.example.mymovielibrary.presentation.ui.lists.state

import com.example.mymovielibrary.domain.lists.model.UserCollection

data class ListState (
    var collections: List<UserCollection> = emptyList()
)