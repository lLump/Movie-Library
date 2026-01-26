package com.example.mymovielibrary.domain.lists.repository

import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface ListsRepo {
    // Collections
    suspend fun getUserCollections(): Result<List<UserCollection>, DataError>
    // Collections

    // Favorites
    suspend fun getFavoriteMovies(): Result<List<MediaItem>, DataError>
    suspend fun getFavoriteTvShows(): Result<List<MediaItem>, DataError>
    // Favorites

    // Rated
    suspend fun getRatedMovies(): Result<List<MediaItem>, DataError>
    suspend fun getRatedTvShows(): Result<List<MediaItem>, DataError>
    // Rated

    // Watchlist
    suspend fun getWatchlistMovies(): Result<List<MediaItem>, DataError>
    suspend fun getWatchlistTvShows(): Result<List<MediaItem>, DataError>
    // Watchlist
}