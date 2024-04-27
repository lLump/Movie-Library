package com.example.mymovielibrary.domain.account.helper

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay

interface AccountHelper {
    var isLanguagesLoaded: Boolean
    var isProfileLoaded: Boolean

    suspend fun loadLanguages(callback: (List<LanguageDetails>) -> Unit)
    suspend fun loadProfileDisplay(callback: (ProfileDisplay) -> Unit)
}