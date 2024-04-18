package com.example.mymovielibrary.domain.account.helper

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay

interface ProfileHelper {
    var isLanguagesLoaded: Boolean
    var isProfileLoaded: Boolean

    fun loadLanguages(callback: (List<LanguageDetails>) -> Unit)
    fun loadProfileDisplay(callback: (ProfileDisplay) -> Unit)
}