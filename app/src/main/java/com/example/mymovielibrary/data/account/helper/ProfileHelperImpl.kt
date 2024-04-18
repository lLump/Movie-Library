package com.example.mymovielibrary.data.account.helper

import androidx.compose.ui.graphics.ImageBitmap
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.ProfileRepository
import com.example.mymovielibrary.domain.account.helper.ProfileHelper
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProfileHelperImpl(
    private val scope: CoroutineScope,
    private val accConfig: ProfileRepository,
    private val imageRepo: ImageRepository
) : ProfileHelper, UiEventListener {
    private lateinit var sendUiEvent: suspend (UiEvent) -> Unit

    override var isLanguagesLoaded = false
    override var isProfileLoaded = false

    override fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit) {
        sendUiEvent = collectUiEvent
    }

    override fun loadLanguages(callback: (List<LanguageDetails>) -> Unit) {
        if(!isLanguagesLoaded) {
            scope.launch {
                val list = executeApiCall { accConfig.getLanguages() }
                if (!list.isNullOrEmpty()) {
                    isLanguagesLoaded = true
                    callback(list)
                }
            }
        }
    }

    override fun loadProfileDisplay(callback: (ProfileDisplay) -> Unit) {
        if(!isProfileLoaded) {
            scope.launch {
                val profileDetails = executeApiCall { accConfig.getProfileDetails() }
                if (profileDetails != null) {
                    isProfileLoaded = true

                    val displayProfile = ProfileDisplay(
                        avatar = loadAvatar(profileDetails.avatarPath),
                        username = profileDetails.username,
                        name = profileDetails.name,
                        languageIso = profileDetails.languageIso
                    )

                    callback(displayProfile)
                }
            }
        }
    }

    private suspend fun loadAvatar(path: String): ImageBitmap {
        val size = ImageSize.W500
        val icon = executeApiCall { imageRepo.getIcon(size, path) }
        return icon?: ImageBitmap(1,1)
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