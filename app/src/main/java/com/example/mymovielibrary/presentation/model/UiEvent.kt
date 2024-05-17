package com.example.mymovielibrary.presentation.model

import com.example.mymovielibrary.presentation.model.uiText.UiText

sealed interface UiEvent {
    data class Error(val error: UiText) : UiEvent
}

interface UiEventListener {
    fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit)
}