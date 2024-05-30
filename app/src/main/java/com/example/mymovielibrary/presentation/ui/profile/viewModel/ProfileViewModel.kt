package com.example.mymovielibrary.presentation.ui.profile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.model.events.AccountEvent
import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.domain.model.events.AuthEvent.*
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.domain.model.events.ProfileEvent.*
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.data.storage.TmdbData.clear
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileState
import com.example.mymovielibrary.presentation.ui.profile.state.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authHelper: AuthHelper,
    private val profileHelper: AccountHelper,
): BaseViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        (authHelper as BaseHelper).setCollector(this::sendUiEvent)
        (profileHelper as BaseHelper).setCollector(this::sendUiEvent)
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AuthEvent -> when (event) {
                Login -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val token = authHelper.getRequestToken()
                        _token.postValue(token)
                        TmdbData.requestToken = token
                    }
                }
                //not _token.value because of it deletes after approving (redirecting to url -> restart app)
                ApproveToken -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        authHelper.finishAuth(TmdbData.requestToken)
                        loadProfile()
                    }
                }

                Logout -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        _profileState.emit(
                            _profileState.value.copy(
                                userDetails = UserType.Guest
                            )
                        )
                        TmdbData.clear()
                        authHelper.logout()
                    }
                }
            }

            is ProfileEvent -> when (event) {
                LoadUserDetails -> viewModelScope.launch(Dispatchers.IO) {
                    val loadProfileTask = async { loadProfile() }
                    val loadStatsTask = async { loadUserStats() }
                    loadProfileTask.await()
                    loadStatsTask.await()
                }
                is SaveLanguage -> TmdbData.languageIso = event.language.iso
            }
        }
    }

    private suspend fun loadUserStats() {
        val stats = profileHelper.loadUserStats()
        _profileState.emit(
            _profileState.value.copy(
                userStats = stats
            )
        )
    }

    private suspend fun loadProfile() {
        val profile = profileHelper.loadProfileData()
//            val languages = profileHelper.loadLanguages()
        if (profile != null) {
            _profileState.emit(
                _profileState.value.copy(
                    userDetails = UserType.LoggedIn(profile),
                )
            )
        } else {
            _profileState.emit(
                _profileState.value.copy(
                    userDetails = UserType.Guest
                )
            )
        }
    }
}