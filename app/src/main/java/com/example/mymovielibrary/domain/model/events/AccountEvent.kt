package com.example.mymovielibrary.domain.model.events

sealed interface AccountEvent {
    data object Login : AccountEvent
    data object CheckIsUserLoggedOut : AccountEvent
}