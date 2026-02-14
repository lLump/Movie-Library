package com.example.mymovielibrary.presentation.ui.base.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import com.example.mymovielibrary.presentation.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel: ViewModel() {
    private val eventChannel = Channel<UiEvent>()
    val uiEvents = eventChannel.receiveAsFlow()

    suspend fun sendUiEvent(event: UiEvent) {
        eventChannel.send(event)
    }

    suspend fun <D> request(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                Log.e("base_request", result.error.toString())
//                eventChannel.send(UiEvent.Error(result.asErrorUiText())) //stop coroutine forever if no one listening
//                eventChannel.trySend(UiEvent.Error(result.asErrorUiText())) // do not stop (not suspend)
                null
            }
        }
    }
}