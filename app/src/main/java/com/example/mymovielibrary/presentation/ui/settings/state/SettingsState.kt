package com.example.mymovielibrary.presentation.ui.settings.state

import com.example.mymovielibrary.domain.account.model.LanguageDetails

data class SettingsState(
    val language: LanguageDetails = LanguageDetails("", "") //fixme
)