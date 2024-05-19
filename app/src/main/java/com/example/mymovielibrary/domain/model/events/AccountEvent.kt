package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.account.model.LanguageDetails

sealed interface AccountEvent

sealed interface ProfileEvent: AccountEvent {
    data object LoadUserDetails : ProfileEvent
    data class SaveLanguage(val language: LanguageDetails) : ProfileEvent
}

sealed interface AuthEvent: AccountEvent {
    data object ApproveToken : AuthEvent
    data object Login : AuthEvent
    data object Logout : AuthEvent
}