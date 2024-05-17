package com.example.mymovielibrary.data.account.helper

import android.graphics.Bitmap
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.data.base.helper.BaseHelper
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay
import com.example.mymovielibrary.presentation.viewmodel.states.UserStats

class AccountHelperImpl(
    private val accConfig: AccountRepository,
    private val imageRepo: ImageRepository
) : AccountHelper, BaseHelper() {
    private var isLanguagesLoaded = false
    private var isUserDetailsLoaded = false

    override suspend fun loadLanguages(): List<LanguageDetails> {
        if (!isLanguagesLoaded) {
            val list = request { accConfig.getLanguages() }
            if (!list.isNullOrEmpty()) {
                isLanguagesLoaded = true
                return list
            }
        }
        return emptyList()
    }

    override suspend fun loadProfileData(): ProfileDisplay? {
//        if (!isUserDetailsLoaded && TmdbData.accountIdV4 != "noId") {
            val profileDetails = request { accConfig.getProfileDetails(TmdbData.sessionId) }
            if (profileDetails == null) {
                return null //request error
            } else {
                isUserDetailsLoaded = true
                val displayProfile = ProfileDisplay(
                    avatarPath = profileDetails.avatarPath ?: "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg", //default photo path
                    username = profileDetails.username,
//                    name = profileDetails.name,
                    stats = UserStats(), //TODO USER STATS
                    languageIso = profileDetails.languageIso,
                )
                TmdbData.accountIdV3 = profileDetails.id
                return displayProfile
            }
//        } else { //no access (already loaded or not logged in)
//            sendUiEvent(UiEvent.Error(UiText.DynamicString("No access"))) //FIXME
//            return null
//        }
    }

    private suspend fun loadAvatar(path: String?): Bitmap {
        val size = ImageSize.ORIGINAL
        suspend fun defaultPhoto(): Bitmap = //Default photo from TMDB
            request { imageRepo.getIcon(size, "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg") }
                ?: Bitmap.createBitmap(15, 15, Bitmap.Config.ARGB_8888)
        return if (path == null) defaultPhoto()
        else request { imageRepo.getIcon(size, path) } ?: defaultPhoto()
    }
}