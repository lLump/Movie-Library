package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.domain.model.events.ListEvent.*
import com.example.mymovielibrary.presentation.ui.lists.state.CollectionState
import com.example.mymovielibrary.presentation.ui.lists.state.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listHelper: ListHelper
): BaseViewModel() {

    private val _listsState = MutableStateFlow(ListState())
    val listsState = _listsState.asStateFlow()

    private val _chosenCollectionState = MutableStateFlow(CollectionState())
    val collectionState = _chosenCollectionState.asStateFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            LoadScreen -> loadAllListOnScreen()
            is LoadChosenCollection -> loadChosenCollection(event.id)
            LoadCollections -> loadUserCollections()
            LoadFavorites -> loadFavorites()
            LoadRated -> loadRated()
            LoadWatchlist -> loadWatchlist()
        }
    }

    private fun loadUserCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            _listsState.emit(
                _listsState.value.copy(
                    isLoading = false,
                    userCollections = listHelper.getUserCollections()
                )
            )
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _listsState.emit(
                _listsState.value.copy(
                    isLoading = false,
                    favorite = listHelper.getFavorites()
                )
            )
        }
    }

    private fun loadRated() {
        viewModelScope.launch(Dispatchers.IO) {
            _listsState.emit(
                _listsState.value.copy(
                    isLoading = false,
                    rated = listHelper.getRated()
                )
            )
        }
    }

    private fun loadWatchlist() {
        viewModelScope.launch(Dispatchers.IO) {
            _listsState.emit(
                _listsState.value.copy(
                    isLoading = false,
                    watchlist = listHelper.getWatchlist()
                )
            )
        }
    }

    private fun loadChosenCollection(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val collectionDetails = listHelper.getCollectionDetails(collectionId)
            if (collectionDetails != null) {
                _chosenCollectionState.emit(
                    _chosenCollectionState.value.copy(
                        isLoading = false,
                        collection = collectionDetails
                    )
                )
            }
        }
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