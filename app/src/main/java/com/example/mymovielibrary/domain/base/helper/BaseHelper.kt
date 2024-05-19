package com.example.mymovielibrary.domain.base.helper

import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.presentation.ui.util.UiEvent
import com.example.mymovielibrary.presentation.ui.util.uiText.asErrorUiText

abstract class BaseHelper {
    lateinit var sendUiEvent: suspend (UiEvent) -> Unit

    fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit) {
        sendUiEvent = collectUiEvent
    }

    suspend fun <D> request(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                sendUiEvent(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}