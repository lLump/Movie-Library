package com.example.mymovielibrary.data.remote.lists.repository

import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.data.remote.lists.api.HomeListsApi
import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow
import com.example.mymovielibrary.data.remote.lists.util.toMediaItem
import com.example.mymovielibrary.data.remote.lists.util.toMedias
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.repository.HomeListsRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class HomeListsRepoImpl(private val api: HomeListsApi, localStore: LocalStoreReader): HomeListsRepo, BaseRepository(localStore) {
    override suspend fun getAllTrending(timeWindow: TimeWindow): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API All trending ERROR") {
            val response = api.getAllTrending(timeWindow.value)

            response.toMedias()
        }
    }

    override suspend fun getPopularMovies(page: Int): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Popular movies ERROR") {
            val response = api.getPopularMovies(page)

            response.results.map { it.toMediaItem() }
        }
    }

    override suspend fun getPopularTvShows(page: Int): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API Popular tvShows ERROR") {
            val response = api.getPopularTvShows(page)

            response.results.map { it.toMediaItem() }
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API TopRated movies ERROR") {
            val response = api.getTopRatedMovies(page)

            response.results.map { it.toMediaItem() }
        }
    }

    override suspend fun getTopRatedTvShows(page: Int): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API TopRated tvShows ERROR") {
            val response = api.getTopRatedTvShows(page)

            response.results.map { it.toMediaItem() }
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API NowPlaying movies ERROR") {
            val response = api.getNowPlayingMovies(page)

            response.results.map { it.toMediaItem() }
        }
    }

    override suspend fun getNowPlayingTvShows(page: Int): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API NowPlaying tvShows ERROR") {
            val response = api.getNowPlayingTvShows(page)

            response.results.map { it.toMediaItem() }
        }
    }

}