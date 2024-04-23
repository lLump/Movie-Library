package com.example.mymovielibrary.presentation.viewmodel.states

import com.example.mymovielibrary.domain.lists.model.UserCollection

data class ListState (
    var collections: List<UserCollection> = emptyList()
)