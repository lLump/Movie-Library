package com.example.mymovielibrary.data.remote.lists.repository

import com.example.mymovielibrary.data.remote.lists.api.UserListsApi
import com.example.mymovielibrary.data.remote.lists.util.toUserCollection
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.data.remote.lists.util.toMediaUI
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.UserListsRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class UserListsRepoImpl(private val api: UserListsApi, localStore: LocalStoreReader) : UserListsRepo, BaseRepository(localStore) {
    private val accountId: String
        get() = localStore.accountIdV4 ?: throw Exception("No accountIdV4 provided in UserListsRepo")

    override suspend fun getUserCollections(): Result<List<UserCollection>, DataError> {
        return safeApiCall(errorMessage = "API User collections ERROR") {
            val response = api.getUserCollections(accountId)

            response.results.map { it.toUserCollection() }
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Favorite Movies ERROR") {
            val response = api.getFavoriteMovies(accountId)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getFavoriteTvShows(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Favorite TV Shows ERROR") {
            val response = api.getFavoriteTvShows(accountId)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getRatedMovies(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Rated Movies ERROR") {
            val response = api.getRatedMovies(accountId)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getRatedTvShows(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Rated TV Shows ERROR") {
            val response = api.getRatedTvShows(accountId)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getWatchlistMovies(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Watchlist Movies ERROR") {
            val response = api.getWatchlistMovies(accountId)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getWatchlistTvShows(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Watchlist TV Shows ERROR") {
            val response = api.getWatchlistTvShows(accountId)

            response.results.map { it.toMediaUI() }
        }
    }
}