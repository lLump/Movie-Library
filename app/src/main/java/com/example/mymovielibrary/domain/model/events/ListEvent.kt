package com.example.mymovielibrary.domain.model.events

sealed interface ListEvent {
    data object LoadScreen: ListEvent
    data class LoadChosenCollection(val id: Int): ListEvent
    data object LoadChosenList: ListEvent
}