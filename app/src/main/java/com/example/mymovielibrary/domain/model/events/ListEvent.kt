package com.example.mymovielibrary.domain.model.events

sealed interface ListEvent {
    data object LoadScreen: ListEvent
    data class CreateCollection(val name: String, val description: String, val isPublic: Boolean): ListEvent
}