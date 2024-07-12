package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.domain.model.events.ListEvent.CreateCollection
import com.example.mymovielibrary.domain.model.events.ListEvent.LoadScreen
import com.example.mymovielibrary.presentation.ui.lists.state.DefaultListsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefaultListsViewModel @Inject constructor(
    private val listHelper: ListHelper,
): BaseViewModel() {

    private val _listsState = MutableStateFlow(DefaultListsState())
    val listsState = _listsState.asStateFlow()

    init {
        (listHelper as BaseHelper).setCollector(this::sendUiEvent)
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            LoadScreen -> loadAllListOnScreen()
            CreateCollection -> createCollection()
        }
    }

    private fun createCollection() {

    }

    private fun loadAllListOnScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskCollections = async { listHelper.getUserCollections() }
            val taskFavorites = async { listHelper.getFavorites() }
            val taskRated = async { listHelper.getRated() }
            val taskWatchlist = async { listHelper.getWatchlist() }

            val collections = taskCollections.await()
            val favorites = taskFavorites.await()
            val rated = taskRated.await()
            val watchlist = taskWatchlist.await()

            _listsState.emit(
                _listsState.value.copy(
                    isLoading = false,
                    userCollections = collections,
                    watchlist = watchlist,
                    rated = rated,
                    favorite = favorites
                )
            )
        }
    }
}