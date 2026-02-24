package com.example.mymovielibrary.presentation.ui.lists.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.model.sortedByTitle
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.UserListsRepo
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.local.SettingsWriter
import com.example.mymovielibrary.domain.model.events.ListEvent
import com.example.mymovielibrary.domain.model.events.ListEvent.CreateCollection
import com.example.mymovielibrary.domain.model.events.ListEvent.LoadScreen
import com.example.mymovielibrary.presentation.ui.lists.state.UserListsState
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
    private val userListsRepo: UserListsRepo,
    private val collectionRepo: CollectionRepo,
    private val settingsWriter: SettingsWriter
): BaseViewModel() {

    private val _listsState = MutableStateFlow(UserListsState())
    val listsState = _listsState.asStateFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            LoadScreen -> loadAllListOnScreen()
            is CreateCollection -> createCollection(event.name, event.description, event.isPublic)
        }
    }

    private fun createCollection(name: String, description: String, isPublic: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            request { collectionRepo.createCollection(name, description, isPublic) }
            val userCollections = request { userListsRepo.getUserCollections() } ?: listOf()
            withContext(Dispatchers.Main) {
                _listsState.emit(
                    _listsState.value.copy(
                        userCollections = userCollections
                    )
                )
            }
            settingsWriter.saveUserCollections(userCollections.map { UserCollectionInfo(it.id, it.name) })
        }
    }

    private fun loadAllListOnScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskCollections = async { request { userListsRepo.getUserCollections() } }
            val taskFavMovie = async { request { userListsRepo.getFavoriteMovies() } }
            val taskFavTv = async { request { userListsRepo.getFavoriteTvShows() } }
            val taskRatedMovie = async { request { userListsRepo.getRatedMovies() } }
            val taskRatedTv = async { request { userListsRepo.getRatedTvShows() } }
            val taskWatchlistMovie = async { request { userListsRepo.getWatchlistMovies() } }
            val taskWatchlistTv = async { request { userListsRepo.getWatchlistTvShows() } }

            val collections = taskCollections.await()
            val favoritesMovie = taskFavMovie.await()
            val favoritesTv = taskFavTv.await()
            val ratedMovie = taskRatedMovie.await()
            val ratedTv = taskRatedTv.await()
            val watchlistMovie = taskWatchlistMovie.await()
            val watchlistTv = taskWatchlistTv.await()

            withContext(Dispatchers.Main) {
                _listsState.emit(
                    UserListsState(
                        isLoading = false,
                        userCollections = collections ?: listOf(),
                        watchlist = (watchlistMovie ?: listOf()) + (watchlistTv ?: listOf()).sortedByTitle(),
                        rated = (ratedMovie ?: listOf()) + (ratedTv ?: listOf()).sortedByTitle(),
                        favorite = (favoritesMovie ?: listOf()) + (favoritesTv ?: listOf()).sortedByTitle()
                    )
                )
            }
            settingsWriter.saveUserCollections((collections ?: listOf()).map { UserCollectionInfo(it.id, it.name) })
        }
    }
}