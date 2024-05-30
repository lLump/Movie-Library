package com.example.mymovielibrary.data.account.helper

import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats

class AccountHelperImpl(
    private val accConfig: AccountRepository,
    private val listRepo: ListRepository,
) : AccountHelper, BaseHelper() {

    override suspend fun loadLanguages(): List<LanguageDetails> {
        val list = request { accConfig.getLanguages() }
        return if (!list.isNullOrEmpty()) {
            list
        } else {
            emptyList()
        }
    }

    override suspend fun loadProfileData(): ProfileDisplay? {
        val profileDetails = request { accConfig.getProfileDetails(TmdbData.sessionId) }
        if (profileDetails == null) {
            return null //request error
        } else {
            val displayProfile = ProfileDisplay(
                avatarPath = profileDetails.avatarPath ?: "2Fj7wrz6ikBMZXx6NBwjDMH3JpHWh.jpg", //default photo path
                username = profileDetails.username,
                stats = getUserStats(),
//                    name = profileDetails.name,
                languageIso = profileDetails.languageIso,
            )
            TmdbData.accountIdV3 = profileDetails.id
            return displayProfile
        }
    }

    private suspend fun getUserStats(): UserStats {
        val watched = 0
        val planned = getWatchlistSize()
        val rated = getRatedSize()
        val favorites = getFavoriteSize()

        return UserStats(
            watched = watched.toString(),
            planned = planned,
            rated = rated,
            favorite = favorites
        )
    }

    private suspend fun getWatchlistSize(): String {
        val movies = request { listRepo.getWatchlistMovies() } ?: emptyList()
        val tvs = request { listRepo.getWatchlistTvShows() } ?: emptyList()

        return (movies + tvs).size.toString()
    }

    private suspend fun getRatedSize(): String {
        val movies = request { listRepo.getRatedMovies() } ?: emptyList()
        val tvs = request { listRepo.getRatedTvShows() } ?: emptyList()

        return (movies + tvs).size.toString()
    }

    private suspend fun getFavoriteSize(): String {
        val movies = request { listRepo.getFavoriteMovies() } ?: emptyList()
        val tvs = request { listRepo.getFavoriteTvShows() } ?: emptyList()

        return (movies + tvs).size.toString()
    }

}