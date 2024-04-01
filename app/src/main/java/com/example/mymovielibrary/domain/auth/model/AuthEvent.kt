package com.example.mymovielibrary.domain.auth.model

sealed interface Event

sealed interface AuthEvent: Event {
    data class LoginSession(val user: UserInfo, val needToSave: Boolean) : AuthEvent
    data object GuestSession : AuthEvent
}

sealed class CustomEvent: Event {
    data object OnStartUp: CustomEvent()
}