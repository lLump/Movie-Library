package com.example.mymovielibrary.domain.model.events

sealed interface ListEvent {
    data object LoadScreen: ListEvent
    data class LoadChosenCollection(val id: Int): ListEvent
    data object LoadCollections: ListEvent
    data object LoadWatchlist: ListEvent
    data object LoadRated: ListEvent
    data object LoadFavorites: ListEvent
}