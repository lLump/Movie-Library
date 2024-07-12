package com.example.mymovielibrary.domain.model.events

sealed interface ListEvent {
    data object LoadScreen: ListEvent
    data object CreateCollection: ListEvent
}