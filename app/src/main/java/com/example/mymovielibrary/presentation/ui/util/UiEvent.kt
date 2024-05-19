package com.example.mymovielibrary.presentation.ui.util

import com.example.mymovielibrary.presentation.ui.util.uiText.UiText

sealed interface UiEvent {
    data class Error(val error: UiText) : UiEvent
    data object Initial: UiEvent
}