package com.example.mymovielibrary.domain.lists.repository

import com.example.mymovielibrary.domain.lists.model.Movie
import com.example.mymovielibrary.domain.lists.model.TVShow
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface ListsRepo {
    // Collections
    suspend fun getUserCollections(): Result<List<UserCollection>, DataError>
    // Collections

    // Favorites
    suspend fun getFavoriteMovies(): Result<List<Movie>, DataError>
    suspend fun getFavoriteTvShows(): Result<List<TVShow>, DataError>
    // Favorites

    // Rated
    suspend fun getRatedMovies(): Result<List<Movie>, DataError>
    suspend fun getRatedTvShows(): Result<List<TVShow>, DataError>
    // Rated

    // Watchlist
    suspend fun getWatchlistMovies(): Result<List<Movie>, DataError>
    suspend fun getWatchlistTvShows(): Result<List<TVShow>, DataError>
    // Watchlist
}