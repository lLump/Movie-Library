package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.auth.model.UserInfo

sealed interface AuthEvent: Event {
    data class LoginSession(val user: UserInfo, val needToSave: Boolean) : AuthEvent
    data object GuestSession : AuthEvent
}