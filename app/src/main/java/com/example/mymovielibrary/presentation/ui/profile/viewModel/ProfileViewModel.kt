package com.example.mymovielibrary.presentation.ui.profile.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.repository.UserListsRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.model.events.AccountEvent
import com.example.mymovielibrary.domain.model.events.AccountEvent.*
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileState
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats
import com.example.mymovielibrary.presentation.ui.profile.state.UserType
import com.example.mymovielibrary.presentation.ui.profile.state.UserType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val localStoreReader: LocalStoreReader,
    private val localStoreWriter: LocalStoreWriter,
    private val accConfig: AccountRepo,
    private val userListsRepo: UserListsRepo,
): BaseViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        loadUserScreen()
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            Login -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val token = getRequestToken()
                    localStoreWriter.saveTempRequestToken(token)
                    withContext(Dispatchers.Main) {
                        _profileState.emit(
                            ProfileState(
                                isApproved = false,
                                userDetails = NeedApproval(token),
                            )
                        )
                    }
                }
            }
            ApproveToken -> { // когда вернулся с сайта
                viewModelScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        _profileState.emit(
                            ProfileState(
                                userDetails = Loading
                            )
                        )
                    }
                    finishAuth(localStoreReader.requestToken ?: throw Exception("RequestToken was not provided"))
                    loadUserScreen()
                }
            }
            LoadUserScreen -> loadUserScreen()
        }
    }

    private fun loadUserScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            if (localStoreReader.accessToken.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _profileState.emit(
                        ProfileState(
                            isApproved = false,
                            userDetails = Guest,
                        )
                    )
                }
                return@launch
            }
            val userStatsTask = async { loadUserStats() }
            val profileTask = async { loadProfileData() }

            val userStats = userStatsTask.await()
            val userDetails = profileTask.await()

            withContext(Dispatchers.Main) {
                _profileState.emit(
                    ProfileState(
                        isApproved = true,
                        userDetails = userDetails,
                        userStats = userStats
                    )
                )
            }
        }
    }

    private suspend fun loadUserStats(): UserStats = coroutineScope {
        val taskFavMovie = async { request { userListsRepo.getFavoriteMovies() } }
        val taskFavTv = async { request { userListsRepo.getFavoriteTvShows() } }
        val taskRatedMovie = async { request { userListsRepo.getRatedMovies() } }
        val taskRatedTv = async { request { userListsRepo.getRatedTvShows() } }
        val taskWatchlistMovie = async { request { userListsRepo.getWatchlistMovies() } }
        val taskWatchlistTv = async { request { userListsRepo.getWatchlistTvShows() } }

        val favoritesMovie = taskFavMovie.await() ?: listOf()
        val favoritesTv = taskFavTv.await() ?: listOf()
        val ratedMovie = taskRatedMovie.await() ?: listOf()
        val ratedTv = taskRatedTv.await() ?: listOf()
        val watchlistMovie = taskWatchlistMovie.await() ?: listOf()
        val watchlistTv = taskWatchlistTv.await() ?: listOf()

        return@coroutineScope UserStats(
            watched = "", //TODO watched
            planned = (watchlistMovie.count() + watchlistTv.count()).toString(),
            rated = (ratedMovie.count() + ratedTv.count()).toString(),
            favorite = (favoritesMovie.count() + favoritesTv.count()).toString()
        )
    }

    private suspend fun loadProfileData(): UserType {
        val profileDetails = request { accConfig.getProfileDetails() }
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

    private suspend fun finishAuth(requestToken: String) {
        val (accountId, token) = request { authRepo.createAccessTokenV4(requestToken) }
            ?: Pair("", "")
        val sessionId = request { authRepo.getSessionIdV4(token) } ?: throw Exception("getSessionIdV4 == null") //fixme(сомнительно)

        localStoreWriter.saveUserInfo(
            accountIdV4 = accountId,
            sessionId = sessionId,
            accessToken = token
        )
    }
}