package com.example.mymovielibrary.data.account.helper

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay

class AccountHelperImpl(
    private val accConfig: AccountRepository,
    private val imageRepo: ImageRepository
) : AccountHelper, UiEventListener {
    private lateinit var sendUiEvent: suspend (UiEvent) -> Unit
    override fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit) {
        sendUiEvent = collectUiEvent
    }

    override var isLanguagesLoaded = false
    override var isProfileLoaded = false

    override suspend fun loadLanguages(callback: (List<LanguageDetails>) -> Unit) {
        if (!isLanguagesLoaded) {
            val list = executeApiCall { accConfig.getLanguages() }
            if (!list.isNullOrEmpty()) {
                isLanguagesLoaded = true
                callback(list)
            }
        }
    }

    override suspend fun loadProfileDisplay(callback: (ProfileDisplay) -> Unit) {
        if (!isProfileLoaded && TmdbData.accountIdV4 != "noId") {
            val profileDetails = executeApiCall { accConfig.getProfileDetails(TmdbData.sessionId) }
            if (profileDetails != null) {
                isProfileLoaded = true
                val displayProfile = ProfileDisplay(
                    avatar = loadAvatar(profileDetails.avatarPath),
                    username = profileDetails.username,
                    name = profileDetails.name,
                    languageIso = profileDetails.languageIso
                )
                TmdbData.accountIdV3 = profileDetails.id
                callback(displayProfile)
            }
        }
    }

    private suspend fun loadAvatar(path: String?): Bitmap {
        val size = ImageSize.ORIGINAL
        suspend fun defaultPhoto(): Bitmap = //Default photo from TMDB
            executeApiCall { imageRepo.getIcon(size, "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg") }
                ?: Bitmap.createBitmap(15, 15, Bitmap.Config.ARGB_8888)
        return if (path == null) defaultPhoto()
        else executeApiCall { imageRepo.getIcon(size, path) } ?: defaultPhoto()
    }

    private suspend fun <D> executeApiCall(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                sendUiEvent(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}