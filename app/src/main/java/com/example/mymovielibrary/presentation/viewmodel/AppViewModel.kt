package com.example.mymovielibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.account.helper.ProfileHelper
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.events.*
import com.example.mymovielibrary.domain.model.events.AuthEvent.*
import com.example.mymovielibrary.domain.model.events.ProfileEvent.*
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authHelper: AuthHelper,
    private val profileHelper: ProfileHelper,
) : ViewModel() {
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        _profileState.value.listLanguages
        (authHelper as UiEventListener).setCollector(this::collectUiEvent)
        (profileHelper as UiEventListener).setCollector(this::collectUiEvent)
    }

    fun onEvent(event: Event) {
        when (event) {
            is AuthEvent -> when (event) {
                is LoginSession -> {
                    authHelper.performLogin(
                        user = event.user,
                        needToSave = event.needToSave
                    )
                }

                GuestSession -> authHelper.guestLogin()
            }

            is ProfileEvent -> when (event) {
                LoadLanguages -> {
                    profileHelper.loadLanguages { languages ->
                        profileState.value.listLanguages = languages
                    }
                }

                LoadProfile -> {
                    profileHelper.loadProfileDisplay { profile ->
//                        profileState.value.profileType = ProfileType.LoggedIn(profile)
                        profileState.value.user = profile
                    }
                }

                is SaveLanguage -> {
                    TmdbData.languageIso = event.language.iso
                }
            }
        }
    }

    private suspend fun collectUiEvent(event: UiEvent) {
        eventChannel.send(event)
    }

    // FIXME (If user saved, start screen is Main otherwise - Auth.
    //       But also if saved we need to autologin him.
    //       That's the reason why authHelper provides start screen.)
    fun getStartScreen() = authHelper.getStartScreen()


    private suspend fun <D> executeApiCall(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                eventChannel.send(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}