package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.domain.lists.model.sortedByTitle
import com.example.mymovielibrary.domain.lists.repository.UserListsRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManagerRepo
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.model.events.MediaEvent
import com.example.mymovielibrary.domain.model.events.MediaEvent.DeleteItems
import com.example.mymovielibrary.domain.model.events.MediaEvent.LoadChosenList
import com.example.mymovielibrary.domain.model.events.MediaEvent.PutItemsInCollection
import com.example.mymovielibrary.domain.model.events.MediaEvent.PutItemsInList
import com.example.mymovielibrary.domain.model.events.MediaEvent.ToggleMediaCheck
import com.example.mymovielibrary.domain.model.events.MediaEvent.ClearMediaChecks
import com.example.mymovielibrary.presentation.ui.lists.state.UniversalListState
import com.example.mymovielibrary.presentation.ui.lists.viewModel.helper.MediaInserter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val userListsRepo: UserListsRepo,
    mediaManager: MediaManagerRepo,
    settingsReader: SettingsReader
): BaseViewModel() {
    private val _listState = MutableStateFlow(UniversalListState(userCollections = settingsReader.userCollections))
    val listState = _listState.asStateFlow()

    private val mediaHelper = MediaInserter(mediaManager)

    fun onEvent(event: MediaEvent) {
        when (event) {
            is LoadChosenList -> loadChosenList(event.listType)
            is DeleteItems -> viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    mediaHelper.putOrDeleteItemsInChosenList(
                        checkedItems = getCheckedItems(event.ids),
                        listType = event.listType,
                        isAdding = false
                    )
                }
                clearChosenItemsInState(event.ids)
            }
            is PutItemsInCollection -> viewModelScope.launch(Dispatchers.IO) {
                mediaHelper.addItemsToCollection(
                    checkedItems = getCheckedItems(event.ids),
                    collectionId = event.collectionId
                )
            }
            is PutItemsInList -> viewModelScope.launch(Dispatchers.IO) {
                mediaHelper.putOrDeleteItemsInChosenList(
                    checkedItems = getCheckedItems(event.ids),
                    listType = event.listType,
                    isAdding = true
                )
            }
            is ToggleMediaCheck -> toggleMediaChecked(event.id)
            ClearMediaChecks -> clearMediaChecks()
        }
    }

    private fun loadChosenList(listType: ListType) {
        viewModelScope.launch {
            val currentList = withContext(Dispatchers.IO) {
                when (listType) {
                    ListType.WATCHLIST -> getWatchlist()
                    ListType.RATED -> getRated()
                    ListType.FAVORITE -> getFavorites()
                    ListType.COLLECTION -> TODO("Impossible scenario(?)")
                }
            }
            _listState.update { state ->
                state.copy(
                    isLoading = false,
                    chosenList = currentList,
                    currentItems = currentList.size
                )
            }
        }
    }

    private suspend fun getWatchlist(): List<MediaItem> {
        val watchMovies = request { userListsRepo.getWatchlistMovies() } ?: return emptyList()
        val watchTvs = request { userListsRepo.getWatchlistTvShows() } ?: return emptyList()
        return (watchMovies + watchTvs).sortedByTitle()
    }

    private suspend fun getRated(): List<MediaItem> {
        val ratedMovies = request { userListsRepo.getRatedMovies() } ?: return emptyList()
        val ratedTvs = request { userListsRepo.getRatedTvShows() } ?: return emptyList()
        return (ratedMovies + ratedTvs).sortedByTitle()
    }

    private suspend fun getFavorites(): List<MediaItem> {
        val favMovies = request { userListsRepo.getFavoriteMovies() } ?: return emptyList()
        val favTvs = request { userListsRepo.getFavoriteTvShows() } ?: return emptyList()
        return (favMovies + favTvs).sortedByTitle()
    }

    private fun clearChosenItemsInState(ids: Set<Int>) {
        val newList = _listState.value.chosenList.filter { it.id !in ids }
        _listState.update { state ->
            state.copy(
                isLoading = false,
                chosenList = newList,
                currentItems = newList.count()
            )
        }
    }

    private fun getCheckedItems(ids: Set<Int>): List<MediaItem> {
        return _listState.value.chosenList.filter { it.id in ids }
    }

    private fun toggleMediaChecked(id: Int) {
        _listState.update { state ->
            state.copy(
                checkedMedias =
                    if (id in state.checkedMedias)
                        state.checkedMedias - id
                    else
                        state.checkedMedias + id
            )
        }
    }

    private fun clearMediaChecks() {
        _listState.update { it.copy(checkedMedias = emptySet()) }
    }
}