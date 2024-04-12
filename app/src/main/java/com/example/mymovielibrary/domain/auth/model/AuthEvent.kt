package com.example.mymovielibrary.domain.auth.model

import com.example.mymovielibrary.domain.model.Event
import com.example.mymovielibrary.data.TempTmdbData

sealed interface AuthEvent: Event {
    data class LoginSession(val user: UserInfo, val needToSave: Boolean) : AuthEvent
    data object GuestSession : AuthEvent
}

sealed interface DataEvent: Event {
    data class TmdbData(val tmdbData: TempTmdbData) : DataEvent
}