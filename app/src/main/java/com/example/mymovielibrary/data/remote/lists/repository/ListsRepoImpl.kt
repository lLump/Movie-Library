package com.example.mymovielibrary.data.remote.lists.repository

import com.example.mymovielibrary.data.remote.lists.api.UserListsApi
import com.example.mymovielibrary.data.remote.lists.model.collection.toUserCollection
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.data.remote.lists.model.media.toMediaUI
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class ListsRepoImpl(private val api: UserListsApi) : ListsRepo, BaseRepository() {

    override suspend fun getUserCollections(): Result<List<UserCollection>, DataError> {
        return safeApiCall(errorMessage = "API User collections ERROR") {
            val response = api.getUserCollections(Store.tmdbData.accountIdV4)

            response.results.map { it.toUserCollection() }
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Favorite Movies ERROR") {
            val response = api.getFavoriteMovies(Store.tmdbData.accountIdV4)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getFavoriteTvShows(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Favorite Movies ERROR") {
            val response = api.getFavoriteTvShows(Store.tmdbData.accountIdV4)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getRatedMovies(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Rated Movies ERROR") {
            val response = api.getRatedMovies(Store.tmdbData.accountIdV4)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getRatedTvShows(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Rated TV Shows ERROR") {
            val response = api.getRatedTvShows(Store.tmdbData.accountIdV4)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getWatchlistMovies(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Watchlist Movies ERROR") {
            val response = api.getWatchlistMovies(Store.tmdbData.accountIdV4)

            response.results.map { it.toMediaUI() }
        }
    }

    override suspend fun getWatchlistTvShows(): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Watchlist TV Shows ERROR") {
            val response = api.getWatchlistTvShows(Store.tmdbData.accountIdV4)

            response.results.map { it.toMediaUI() }
        }
    }
}