package com.example.mymovielibrary.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.auth.domain.model.Event
import com.example.mymovielibrary.auth.domain.model.AuthEvent.*
import com.example.mymovielibrary.auth.domain.model.AuthEvent
import com.example.mymovielibrary.auth.domain.model.CustomEvent.*
import com.example.mymovielibrary.auth.domain.model.CustomEvent
import com.example.mymovielibrary.auth.domain.repository.AuthRepository
import com.example.mymovielibrary.auth.domain.repository.AuthHelper
import com.example.mymovielibrary.auth.domain.repository.UserStore
import com.example.mymovielibrary.core.domain.model.DataError
import com.example.mymovielibrary.core.domain.model.Result
import com.example.mymovielibrary.core.presentation.ui.model.UiEvent
import com.example.mymovielibrary.core.presentation.ui.uiText.asErrorUiText
import com.example.mymovielibrary.auth.presentation.model.LoadingState
import com.example.mymovielibrary.auth.data.repository.AuthHelperImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    authRepo: AuthRepository,
    userStore: UserStore,
) : ViewModel() {
    private val _loadingState = MutableStateFlow(LoadingState.EMPTY)
    val loadingState = _loadingState.asStateFlow()

    private val authHelper: AuthHelper =
        AuthHelperImpl(viewModelScope, authRepo, userStore, _loadingState, this::collectUiEvent)

    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: Event) {
        when (event) {
            is AuthEvent -> {
                when (event) {
                    is LoginSession -> authHelper.login(
                        login = event.user.username,
                        password = event.user.password,
                        needToSave = event.needToSave
                    )

                    GuestSession -> {
                        authHelper.guestLogin()
                    }
                }
            }

            is CustomEvent -> {
                when (event) {
                    OnStartUp -> {
//                        initToken()
//                        checkIfUserSaved()
                    }
                }
            }
        }
    }

    // FIXME (If user saved, start screen is Main otherwise - Auth.
    //       But also if saved we need to autologin him.
    //       That's the reason why authHelper provide start screen.)
    fun getStartScreen() = authHelper.getStartScreen()

    private fun collectUiEvent(event: UiEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

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