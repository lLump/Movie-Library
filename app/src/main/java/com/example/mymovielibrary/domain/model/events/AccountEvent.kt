package com.example.mymovielibrary.domain.model.events

sealed interface AccountEvent {
    data object ApproveToken : AccountEvent
    data object Login : AccountEvent
    data object LoadUserScreen : AccountEvent
}