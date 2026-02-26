package com.example.mymovielibrary.presentation.ui.settings.state

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo

data class SettingsState(
    val language: LanguageDetails = LanguageDetails("", ""),
    val userCollections: List<UserCollectionInfo> = emptyList(),
    val selectedCollections: Set<Int> = emptySet(),
)