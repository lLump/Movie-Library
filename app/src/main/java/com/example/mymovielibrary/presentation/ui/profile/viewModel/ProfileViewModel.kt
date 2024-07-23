package com.example.mymovielibrary.presentation.ui.profile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.local.LocalInfoManagerImpl
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.data.remote.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import com.example.mymovielibrary.domain.model.events.AccountEvent
import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.domain.model.events.AuthEvent.ApproveToken
import com.example.mymovielibrary.domain.model.events.AuthEvent.Login
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.domain.model.events.ProfileEvent.LoadUserScreen
import com.example.mymovielibrary.domain.model.events.ProfileEvent.SaveLanguage
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileState
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats
import com.example.mymovielibrary.presentation.ui.profile.state.UserType
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
    private val authRepo: AuthRepoImpl,
    private val userPrefs: LocalInfoManagerImpl,
    private val accConfig: AccountRepo,
    private val listsRepo: ListsRepo,
): BaseViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AuthEvent -> when (event) {
                Login -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        val token = getRequestToken()
                        _token.postValue(token)
                        Store.tmdbData.requestToken = token
                    }
                }
                //not _token.value because of it deletes after approving (redirecting to url -> restart app)
                ApproveToken -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        finishAuth(Store.tmdbData.requestToken)
//                        loadProfile() //fixme загружало лишь инфу профиля, без статистики
                    }
                }
            }

            is ProfileEvent -> when (event) {
                LoadUserScreen -> loadUserScreen()
                is SaveLanguage -> Store.tmdbData.iso639 = event.language.iso //FIXME
            }
        }
    }

    private fun loadUserScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatsTask = async { loadUserStats() }
            val profileTask = async { loadProfileData() }

            val userStats = userStatsTask.await()
            val profileDisplay = profileTask.await()

            val userDetails =
                if (profileDisplay != null)
                    UserType.LoggedIn(profileDisplay)
                else
                    UserType.Guest

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
        val taskFavMovie = async { request { listsRepo.getFavoriteMovies() } }
        val taskFavTv = async { request { listsRepo.getFavoriteTvShows() } }
        val taskRatedMovie = async { request { listsRepo.getRatedMovies() } }
        val taskRatedTv = async { request { listsRepo.getRatedTvShows() } }
        val taskWatchlistMovie = async { request { listsRepo.getWatchlistMovies() } }
        val taskWatchlistTv = async { request { listsRepo.getWatchlistTvShows() } }

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

    private suspend fun loadProfileData(): ProfileDisplay? {
        val profileDetails = request { accConfig.getProfileDetails(Store.tmdbData.sessionId) }
        if (profileDetails == null) {
            return null //request error
        } else {
            val displayProfile = ProfileDisplay(
                avatarPath = profileDetails.avatarPath ?: "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg", //default photo path
                username = profileDetails.username,
//                stats = getUserStats(),
//                    name = profileDetails.name,
                languageIso = profileDetails.languageIso,
            )
            Store.tmdbData.accountIdV3 = profileDetails.id
            return displayProfile
        }
    }

    private suspend fun getRequestToken() = request { authRepo.createRequestTokenV4() } ?: "noToken"

    private suspend fun finishAuth(requestToken: String) {
        val (accountId, token) = request { authRepo.createAccessTokenV4(requestToken) }
            ?: Pair("noId", "noToken")
        val sessionId = request { authRepo.getSessionIdV4(token) } ?: "noSessionId"

        userPrefs.saveUserInfo(accountId, sessionId, token) //local save into prefs & store
    }
}