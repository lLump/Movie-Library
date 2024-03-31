package com.example.mymovielibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.auth.model.Event
import com.example.mymovielibrary.domain.auth.model.AuthEvent.*
import com.example.mymovielibrary.domain.auth.model.AuthEvent
import com.example.mymovielibrary.domain.auth.model.CustomEvent.*
import com.example.mymovielibrary.domain.auth.model.CustomEvent
import com.example.mymovielibrary.domain.auth.repository.AuthHelper
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.model.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authHelper: AuthHelper,
) : ViewModel(), UiEventListener {
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _loadingState = MutableStateFlow(LoadingState.EMPTY)
//    val loadingState = _loadingState.asStateFlow()

    // Сделать через di, ссилку на ивент установить при помощи функции. Которая будет во втором интерфейсе
    // лоад ивент сделать uiEvent'ом
//    private val authHelper: AuthHelper =
//        AuthHelperImpl(viewModelScope, authRepo, userStore, _loadingState, this::collectUiEvent)


    init {
//        (authHelper as UiEventListener).setCollector(this::collectUiEvent)
        authHelper.setEventListener(this)
    }

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

    override suspend fun collectUiEvent(event: UiEvent) {
        eventChannel.send(event)
    }

    // FIXME (If user saved, start screen is Main otherwise - Auth.
    //       But also if saved we need to autologin him.
    //       That's the reason why authHelper provide start screen.)
    fun getStartScreen() = authHelper.getStartScreen()

//    private fun collectUiEvent(event: UiEvent) {
//        viewModelScope.launch {
//            eventChannel.send(event)
//        }
//    }

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