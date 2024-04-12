package com.example.mymovielibrary.presentation.model

import com.example.mymovielibrary.domain.model.Event
import com.example.mymovielibrary.presentation.model.uiText.UiText

sealed interface UiEvent: Event {
    data class Error(val error: UiText) : UiEvent
    data class Loading(val loading: LoadingState) : UiEvent
}

interface UiEventListener {
    //    fun setCollector(collectUiEvent: (UiEvent) -> Unit)
    suspend fun collectUiEvent(event: UiEvent)
}