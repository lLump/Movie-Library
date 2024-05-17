package com.example.mymovielibrary.domain.account.helper

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay

interface AccountHelper {
    suspend fun loadLanguages(): List<LanguageDetails>
    suspend fun loadProfileData(): ProfileDisplay?
}