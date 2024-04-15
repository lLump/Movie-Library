package com.example.mymovielibrary.domain.model.events

sealed interface ProfileEvent: Event {
    data object LoadLanguages: ProfileEvent
}