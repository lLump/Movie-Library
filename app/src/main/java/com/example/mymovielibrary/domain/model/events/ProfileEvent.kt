package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.images.model.ImageSize

sealed interface ProfileEvent: Event {
    data object LoadLanguages: ProfileEvent
    data object LoadProfile: ProfileEvent
    data class SaveLanguage(val language: LanguageDetails) : ProfileEvent
}