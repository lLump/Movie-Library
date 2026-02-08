package com.example.mymovielibrary.presentation.ui.settings.state

data class SettingsState(
    val language: Language = Language()
    //todo theme
)

data class Language(
    val country: String = "",
    val iso639: String = "",
    val iso3166: String = "",
)