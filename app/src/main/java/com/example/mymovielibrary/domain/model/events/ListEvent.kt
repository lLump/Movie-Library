package com.example.mymovielibrary.domain.model.events

sealed interface ListEvent {
    data object LoadCollections: ListEvent
}