package com.example.mymovielibrary.presentation.viewmodel.states

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mymovielibrary.domain.account.model.LanguageDetails

data class ProfileState (
    var profileType: ProfileType = ProfileType.Guest,
    var listLanguages: List<LanguageDetails> = listOf(LanguageDetails("English", "en")),
    var tmdbLanguage: LanguageDetails = LanguageDetails("English", "en"),
    var appLanguage: String = "English",
)

sealed interface ProfileType {
    data class LoggedIn(val avatar: ImageVector): ProfileType
    data object Guest : ProfileType
}