package com.example.mymovielibrary.core.presentation.ui.model

import com.example.mymovielibrary.core.presentation.ui.uiText.UiText

sealed interface UiEvent {
    data class Error(val error: UiText): UiEvent
//    data class Loading(val loading: LoadingState): UiEvent
}