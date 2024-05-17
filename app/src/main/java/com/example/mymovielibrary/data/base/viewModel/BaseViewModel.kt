package com.example.mymovielibrary.data.base.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.model.events.AccountEvent
import com.example.mymovielibrary.domain.model.events.Event
import com.example.mymovielibrary.presentation.model.UiEvent
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

    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            events.collect { event ->
                when(event) {
                    is UiEvent.Error -> { }
                }
            }
        }
    }
}