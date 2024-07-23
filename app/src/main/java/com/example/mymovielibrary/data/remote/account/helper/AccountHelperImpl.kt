package com.example.mymovielibrary.data.remote.account.helper

import com.example.mymovielibrary.data.remote.account.repository.AccountRepoImpl
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay

class AccountHelperImpl(
    private val accConfig: AccountRepoImpl,
    private val listsRepo: ListsRepo,
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

    override suspend fun getWatchlistSize(): String {
        val movies = request { listsRepo.getWatchlistMovies() } ?: emptyList()
        val tvs = request { listsRepo.getWatchlistTvShows() } ?: emptyList()

        return (movies + tvs).size.toString()
    }

    override suspend fun getRatedSize(): String {
        val movies = request { listsRepo.getRatedMovies() } ?: emptyList()
        val tvs = request { listsRepo.getRatedTvShows() } ?: emptyList()

        return (movies + tvs).size.toString()
    }

    override suspend fun getFavoriteSize(): String {
        val movies = request { listsRepo.getFavoriteMovies() } ?: emptyList()
        val tvs = request { listsRepo.getFavoriteTvShows() } ?: emptyList()

        return (movies + tvs).size.toString()
    }

}