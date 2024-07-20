package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.storage.Store
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.model.sortedByTitle
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.domain.model.events.ListEvent.CreateCollection
import com.example.mymovielibrary.domain.model.events.ListEvent.LoadScreen
import com.example.mymovielibrary.domain.use_cases.CollectionCreatorInterface
import com.example.mymovielibrary.presentation.ui.lists.state.DefaultListsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DefaultListsViewModel @Inject constructor(
    private val listsRepo: ListsRepo,
    private val collectionCreator: CollectionCreatorInterface
): BaseViewModel() {

    private val _listsState = MutableStateFlow(DefaultListsState())
    val listsState = _listsState.asStateFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            LoadScreen -> loadAllListOnScreen()
            is CreateCollection -> createCollection(event.name, event.description, event.isPublic)
        }
    }

    private fun createCollection(name: String, description: String, isPublic: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            request { collectionCreator(name, description, isPublic) }
            withContext(Dispatchers.Main) {
                _listsState.emit(
                    _listsState.value.copy(
                        userCollections = request { listsRepo.getUserCollections() } ?: listOf()
                    )
                )
            }
        }
    }

    private fun loadAllListOnScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskCollections = async { request { listsRepo.getUserCollections() } }
            val taskFavMovie = async { request { listsRepo.getFavoriteMovies() } }
            val taskFavTv = async { request { listsRepo.getFavoriteTvShows() } }
            val taskRatedMovie = async { request { listsRepo.getRatedMovies() } }
            val taskRatedTv = async { request { listsRepo.getRatedTvShows() } }
            val taskWatchlistMovie = async { request { listsRepo.getWatchlistMovies() } }
            val taskWatchlistTv = async { request { listsRepo.getWatchlistTvShows() } }

            val collections = taskCollections.await()
            val favoritesMovie = taskFavMovie.await()
            val favoritesTv = taskFavTv.await()
            val ratedMovie = taskRatedMovie.await()
            val ratedTv = taskRatedTv.await()
            val watchlistMovie = taskWatchlistMovie.await()
            val watchlistTv = taskWatchlistTv.await()

            withContext(Dispatchers.Main) {
                _listsState.emit(
                    DefaultListsState(
                        isLoading = false,
                        userCollections = collections ?: listOf(),
                        watchlist = (watchlistMovie ?: listOf()) + (watchlistTv ?: listOf()).sortedByTitle(),
                        rated = (ratedMovie ?: listOf()) + (ratedTv ?: listOf()).sortedByTitle(),
                        favorite = (favoritesMovie ?: listOf()) + (favoritesTv ?: listOf()).sortedByTitle()
                    )
                )
            }
            Store.userCollections = (collections ?: listOf()).map { Pair(it.id, it.name) }
        }
    }
}