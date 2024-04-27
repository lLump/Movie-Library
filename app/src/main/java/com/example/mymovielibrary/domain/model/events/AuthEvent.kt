package com.example.mymovielibrary.domain.model.events

sealed interface AuthEvent: Event {
    data object LoginSession : AuthEvent
    data object ApproveToken : AuthEvent
    data object GuestSession : AuthEvent
}