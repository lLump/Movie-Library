package com.example.mymovielibrary.presentation.viewmodel.states

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.ImageBitmap
import com.example.mymovielibrary.domain.account.model.LanguageDetails

data class ProfileState(
//    var profileType: ProfileType = ProfileType.Guest,
    var user: ProfileDisplay = ProfileDisplay(ImageBitmap(1, 1), "", "", ""),
    var listLanguages: List<LanguageDetails> = listOf(LanguageDetails("English", "en")),
    var tmdbLanguage: LanguageDetails = LanguageDetails("English", "en"),
    var appLanguage: String = "English",
)

//sealed interface ProfileType {
//    data class LoggedIn(val profile: ProfileDisplay) : ProfileType
//    data object Guest : ProfileType
//}

data class ProfileDisplay(
    val avatar: ImageBitmap, val username: String, val name: String, val languageIso: String
)

