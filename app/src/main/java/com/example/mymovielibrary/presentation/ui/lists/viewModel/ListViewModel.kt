package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.model.ListType
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.sortedByTitle
import com.example.mymovielibrary.domain.lists.repository.ListRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManager
import com.example.mymovielibrary.domain.model.events.MediaEvent
import com.example.mymovielibrary.domain.model.events.MediaEvent.DeleteItems
import com.example.mymovielibrary.domain.model.events.MediaEvent.EditItems
import com.example.mymovielibrary.domain.model.events.MediaEvent.LoadChosenList
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
    private val listRepo: ListRepo,
    private val mediaManager: MediaManager,
): BaseViewModel() {
    private val _listState = MutableStateFlow(ListState())
    val listState = _listState.asStateFlow()

//        (detailListHelper as BaseHelper).setCollector(this::sendUiEvent)

    fun onEvent(event: MediaEvent) {
        when (event) {
            is LoadChosenList -> loadChosenList(event.listType)
            is DeleteItems -> deleteCheckedItems(event.ids, event.type)
            is EditItems -> editCheckedItems(event.ids, event.type)
        }
    }

    private fun loadChosenList(listType: ListType) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentList = when (listType) {
                ListType.WATCHLIST -> getWatchlist()
                ListType.RATED -> getRated()
                ListType.FAVORITE -> getFavorites()
            }
            _listState.emit(
                _listState.value.copy(
                    isLoading = false,
                    chosenList = currentList,
                    currentItems = currentList.size
                )
            )
        }
    }

    private suspend fun getWatchlist(): List<MediaItem> {
        val watchMovies = request { listRepo.getWatchlistMovies() } ?: return emptyList()
        val watchTvs = request { listRepo.getWatchlistTvShows() } ?: return emptyList()
        return (watchMovies + watchTvs).sortedByTitle()
    }

    private suspend fun getRated(): List<MediaItem> {
        val ratedMovies = request { listRepo.getRatedMovies() } ?: return emptyList()
        val ratedTvs = request { listRepo.getRatedTvShows() } ?: return emptyList()
        return (ratedMovies + ratedTvs).sortedByTitle()
    }

    private suspend fun getFavorites(): List<MediaItem> {
        val favMovies = request { listRepo.getFavoriteMovies() } ?: return emptyList()
        val favTvs = request { listRepo.getFavoriteTvShows() } ?: return emptyList()
        return (favMovies + favTvs).sortedByTitle()
    }

    private fun editCheckedItems(ids: List<Int>, listType: ListType) {
        viewModelScope.launch(Dispatchers.IO) {
//            val itemsToEdit = getCheckedItems(ids)
//            clearChosenItemsInState(ids, listType)

        }
    }

    private fun deleteCheckedItems(ids: List<Int>, listType: ListType) {
        viewModelScope.launch(Dispatchers.IO) {
            async { deleteCheckedItemsInApi(ids, listType) }.await()
            async { clearChosenItemsInState(ids) }.await()
        }
    }

    private suspend fun deleteCheckedItemsInApi(ids: List<Int>, listType: ListType) {
        val itemsToDelete = getCheckedItems(ids)
        when (listType) {
            ListType.WATCHLIST -> {
                itemsToDelete.forEach { media ->
                    mediaManager.addOrDeleteInWatchlist(
                        mediaId = media.id,
                        isMovie = media.isMovie,
                        isAdding = false
                    )
                }
            }
            ListType.RATED -> {
                itemsToDelete.forEach { media ->
                    //TODO rated delete
                }
            }
            ListType.FAVORITE -> {
                itemsToDelete.forEach { media ->
                    mediaManager.addOrDeleteInFavorite(
                        mediaId = media.id,
                        isMovie = media.isMovie,
                        isAdding = false
                    )
                }
            }
        }
    }

    private suspend fun clearChosenItemsInState(ids: List<Int>) {
        val newList = _listState.value.chosenList.filter { it.id !in ids }
        _listState.emit(
            _listState.value.copy(
                chosenList = newList,
                currentItems = newList.count()
            )
        )
    }

    private fun getCheckedItems(ids: List<Int>): List<MediaItem> {
       return _listState.value.chosenList.filter { it.id in ids }
    }
}