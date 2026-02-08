package com.example.mymovielibrary.presentation.ui.profile.state

data class ProfileState(
    val userDetails: UserType = UserType.Loading,
    val userStats: UserStats = UserStats("", "", "", ""),
//    val tmdbLanguage: LanguageDetails = LanguageDetails("English", "en"),
//    val appLanguage: String = "",
)

sealed interface UserType {
    data object Loading: UserType
    data object Guest : UserType
    data class NeedApproval(val requestToken: String) : UserType
    data class LoggedIn(val profile: ProfileDisplay) : UserType
}

data class ProfileDisplay(
    val username: String,
    val avatarPath: String,
    val languageIso: String,
)

data class UserStats(
    val watched: String,
    val planned: String,
    val rated: String,
    val favorite: String,
)

