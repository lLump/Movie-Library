package com.example.mymovielibrary.data.lists.repository

import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.model.collection.toUserCollection
import com.example.mymovielibrary.data.lists.model.media.toMovie
import com.example.mymovielibrary.data.lists.model.media.toTvShow
import com.example.mymovielibrary.data.storage.Store
import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.model.Movie
import com.example.mymovielibrary.domain.lists.model.TVShow
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class ListsRepoImpl(private val api: ListApi) : ListsRepo, BaseRepository() {

    override suspend fun getUserCollections(): Result<List<UserCollection>, DataError> {
        return safeApiCall(errorMessage = "API User collections ERROR") {
            val response = api.getUserCollections(Store.tmdbData.accountIdV4)

            response.results.map { it.toUserCollection() }
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<Movie>, DataError> {
        return safeApiCall(errorMessage = "API Favorite Movies ERROR") {
            val response = api.getFavoriteMovies(Store.tmdbData.accountIdV4)

            response.results.map { it.toMovie() }
        }
    }

    override suspend fun getFavoriteTvShows(): Result<List<TVShow>, DataError> {
        return safeApiCall(errorMessage = "API Favorite Movies ERROR") {
            val response = api.getFavoriteTvShows(Store.tmdbData.accountIdV4)

            response.results.map { it.toTvShow() }
        }
    }

    override suspend fun getRatedMovies(): Result<List<Movie>, DataError> {
        return safeApiCall(errorMessage = "API Rated Movies ERROR") {
            val response = api.getRatedMovies(Store.tmdbData.accountIdV4)

            response.results.map { it.toMovie() }
        }
    }

    override suspend fun getRatedTvShows(): Result<List<TVShow>, DataError> {
        return safeApiCall(errorMessage = "API Rated TV Shows ERROR") {
            val response = api.getRatedTvShows(Store.tmdbData.accountIdV4)

            response.results.map { it.toTvShow() }
        }
    }

    override suspend fun getWatchlistMovies(): Result<List<Movie>, DataError> {
        return safeApiCall(errorMessage = "API Watchlist Movies ERROR") {
            val response = api.getWatchlistMovies(Store.tmdbData.accountIdV4)

            response.results.map { it.toMovie() }
        }
    }

    override suspend fun getWatchlistTvShows(): Result<List<TVShow>, DataError> {
        return safeApiCall(errorMessage = "API Watchlist TV Shows ERROR") {
            val response = api.getWatchlistTvShows(Store.tmdbData.accountIdV4)

            response.results.map { it.toTvShow() }
        }
    }
}