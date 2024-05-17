package com.example.mymovielibrary.presentation.viewmodel.states

import com.example.mymovielibrary.domain.account.model.LanguageDetails

data class ProfileState(
    var userDetails: UserType = UserType.Loading,
    var tmdbLanguage: LanguageDetails = LanguageDetails("English", "en"),
    var appLanguage: String = "",
)

sealed interface UserType {
    data object Guest : UserType
    data object Loading: UserType
    data class LoggedIn(val profile: ProfileDisplay) : UserType
}

data class ProfileDisplay(
    val username: String,
    val avatarPath: String,
    val stats: UserStats,
    val languageIso: String,
)

data class UserStats(
    val watched: String = "",
    val planned: String = "",
    val rated: String = "",
    val favorite: String = "",
    val total: String = "",
)

