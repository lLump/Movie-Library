package com.example.mymovielibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authHelper: AuthHelper,
) : ViewModel(), UiEventListener {
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
//        (authHelper as UiEventListener).setCollector(this::collectUiEvent)
        authHelper.setEventListener(this)
    }

    fun onEvent(event: Event) {
        when (event) {
            is AuthEvent -> {
                when (event) {
                    is LoginSession -> authHelper.performLogin(
                        user = event.user,
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