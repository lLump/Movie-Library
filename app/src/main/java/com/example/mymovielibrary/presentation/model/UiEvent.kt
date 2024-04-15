package com.example.mymovielibrary.presentation.model

import com.example.mymovielibrary.domain.model.events.Event
import com.example.mymovielibrary.presentation.model.uiText.UiText
import com.example.mymovielibrary.presentation.viewmodel.states.LoadingState

sealed interface UiEvent: Event {
    data class Error(val error: UiText) : UiEvent
    data class Loading(val loading: LoadingState) : UiEvent
}

interface UiEventListener {
        fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit)
//    suspend fun collectUiEvent(event: UiEvent)
}