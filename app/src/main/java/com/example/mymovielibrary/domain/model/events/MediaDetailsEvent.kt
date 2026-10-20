package com.example.mymovielibrary.domain.model.events

interface MediaDetailsEvent {
//    data class LoadDetails(val id: Int): MediaDetailsEvent
    data object LoadDetails: MediaDetailsEvent
}