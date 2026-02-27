package com.example.mymovielibrary.presentation.ui.profile.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.repository.UserListsRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.model.events.AccountEvent
import com.example.mymovielibrary.domain.model.events.AccountEvent.*
import com.example.mymovielibrary.domain.model.handlers.getOrThrow
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileState
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats
import com.example.mymovielibrary.presentation.ui.profile.state.UserType
import com.example.mymovielibrary.presentation.ui.profile.state.UserType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val accRepo: AccountRepo,
    private val collectionRepo: CollectionRepo,
    private val userListsRepo: UserListsRepo,
    private val localStoreReader: LocalStoreReader,
    private val localStoreWriter: LocalStoreWriter,
    private val settingsReader: SettingsReader,
): BaseViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        if (!isUserLoggedOut()) {
            loadUserScreen()
        }
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            Login -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val token = getRequestToken() //живет 15 минут. А так смело вынес бы в authViewModel
                    localStoreWriter.saveTempRequestToken(token)
                    withContext(Dispatchers.Main) {
                        _profileState.emit(
                            ProfileState(
                                userDetails = NeedApproval(token),
                            )
                        )
                    }
                }
            }
            CheckIsUserLoggedOut -> if (isUserLoggedOut()) {
                viewModelScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        _profileState.emit(
                            ProfileState(
                                userDetails = Guest,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun isUserLoggedOut(): Boolean = localStoreReader.accessToken.isNullOrEmpty()

    private fun loadUserScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatsTask = async { loadUserStats() }
            val profileTask = async { loadProfileData() }

            val userStats = userStatsTask.await()
            val userDetails = profileTask.await()

            withContext(Dispatchers.Main) {
                _profileState.emit(
                    ProfileState(
                        userDetails = userDetails,
                        userStats = userStats
                    )
                )
            }
        }
    }

    private suspend fun loadUserStats(): UserStats = coroutineScope {
        val taskWatchedIds = async { getChosenCollectionsMediasIds() }
        val taskFavMovie = async { request { userListsRepo.getFavoriteMovies() } }
        val taskFavTv = async { request { userListsRepo.getFavoriteTvShows() } }
        val taskRatedMovie = async { request { userListsRepo.getRatedMovies() } }
        val taskRatedTv = async { request { userListsRepo.getRatedTvShows() } }
        val taskWatchlistMovie = async { request { userListsRepo.getWatchlistMovies() } }
        val taskWatchlistTv = async { request { userListsRepo.getWatchlistTvShows() } }

        val watchedIds = taskWatchedIds.await()
        val favoritesMovie = taskFavMovie.await() ?: listOf()
        val favoritesTv = taskFavTv.await() ?: listOf()
        val ratedMovie = taskRatedMovie.await() ?: listOf()
        val ratedTv = taskRatedTv.await() ?: listOf()
        val watchlistMovie = taskWatchlistMovie.await() ?: listOf()
        val watchlistTv = taskWatchlistTv.await() ?: listOf()

        val defaultWatchedIds = (ratedMovie + ratedTv + favoritesMovie + favoritesTv).map { it.id }.toSet()
        watchedIds.addAll(defaultWatchedIds)

        return@coroutineScope UserStats(
            watched = watchedIds.count().toString(),
            planned = (watchlistMovie.count() + watchlistTv.count()).toString(),
            rated = (ratedMovie.count() + ratedTv.count()).toString(),
            favorite = (favoritesMovie.count() + favoritesTv.count()).toString()
        )
    }

    private suspend fun getChosenCollectionsMediasIds(): MutableSet<Int> = coroutineScope {
        val userCollections = request { userListsRepo.getUserCollections() } ?: listOf()
        val chosenCollections =
            userCollections.filter { it.id in settingsReader.userCollectionsForStats }

        return@coroutineScope chosenCollections.map { collection ->
            async {
                collectionRepo
                    .getCollectionDetails(collection.id)
                    .getOrThrow()
                    .movies
                    .map { it.id }
            }
        }
            .awaitAll()
            .flatten()
            .toMutableSet()
    }

    private suspend fun loadProfileData(): UserType {
        val profileDetails = request { accRepo.getProfileDetails() }
            ?: return Guest
        val displayProfile = ProfileDisplay(
            avatarPath = profileDetails.avatarPath
                ?: "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg", //default photo path todo(not found)
            username = profileDetails.username,
//                stats = getUserStats(),
//                    name = profileDetails.name,
            languageIso = profileDetails.languageIso,
        )

        localStoreWriter.saveAccountIdV3(profileDetails.id)
        return LoggedIn(displayProfile)
    }

    private suspend fun getRequestToken() = request { authRepo.createRequestTokenV4() } ?: throw Exception("createRequestToken == null") //fixme(сомнительно)
}