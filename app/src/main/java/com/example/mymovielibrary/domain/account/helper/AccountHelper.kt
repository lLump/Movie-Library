package com.example.mymovielibrary.domain.account.helper

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats

interface AccountHelper {
    suspend fun loadLanguages(): List<LanguageDetails>
    suspend fun loadProfileData(): ProfileDisplay?
    suspend fun loadUserStats(): UserStats
}