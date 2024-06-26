package com.example.mymovielibrary.domain.base.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.presentation.ui.util.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    suspend fun sendUiEvent(event: UiEvent) {
        eventChannel.send(event)
    }
}