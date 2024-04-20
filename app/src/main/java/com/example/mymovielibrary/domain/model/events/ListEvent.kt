package com.example.mymovielibrary.domain.model.events

sealed interface ListEvent: Event {
    data object LoadCollections: ListEvent
}