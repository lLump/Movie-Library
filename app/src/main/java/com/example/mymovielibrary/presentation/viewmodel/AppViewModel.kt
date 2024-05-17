package com.example.mymovielibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.model.events.*
import com.example.mymovielibrary.domain.model.events.ListEvent.*
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.viewmodel.states.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val listHelper: ListHelper
) : ViewModel() {
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _listState = MutableStateFlow(ListState(emptyList()))
    val listState = _listState.asStateFlow()

    init {
//        (authHelper as UiEventListener).setCollector(this::collectUiEvent)
//        (profileHelper as UiEventListener).setCollector(this::collectUiEvent)
        (listHelper as UiEventListener).setCollector(this::collectUiEvent)
    }

    fun onEvent(event: Event) {
        when (event) {

            is ListEvent -> when (event) {
                LoadCollections -> {
                    viewModelScope.launch {
                        _listState.value.collections = listHelper.getUserCollections()
                    }
                }
            }
        }
    }

//    private fun loadProfile() {
//        viewModelScope.launch(Dispatchers.IO) {
//            profileHelper.loadProfileDisplay { userProfile ->
//                profileState.value.user = userProfile
//            }
//            profileHelper.loadLanguages { languages ->
//                profileState.value.listLanguages = languages
//            }
//        }
//    }

    private suspend fun collectUiEvent(event: UiEvent) {
        eventChannel.send(event)
    }
}