package com.example.mymovielibrary.domain.base.viewModel

import androidx.lifecycle.ViewModel
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import com.example.mymovielibrary.presentation.ui.util.UiEvent
import com.example.mymovielibrary.presentation.ui.util.uiText.asErrorUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel: ViewModel() {
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    suspend fun sendUiEvent(event: UiEvent) {
        eventChannel.send(event)
    }

    suspend fun <D> request(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                eventChannel.send(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}