package com.example.mymovielibrary.domain.account.helper

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mymovielibrary.domain.account.model.LanguageDetails

interface ProfileHelper {
    fun loadLanguages(callback: (List<LanguageDetails>) -> Unit)
    fun loadAvatar(): ImageVector
}