package com.example.mymovielibrary.domain.lists.helper

import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.Movie
import com.example.mymovielibrary.domain.lists.model.TVShow
import com.example.mymovielibrary.domain.lists.model.UserCollection

interface ListHelper {
    suspend fun getUserCollections(): List<UserCollection>
    suspend fun getCollectionDetails(collectionId: Int): CollectionDetails?

    suspend fun getFavoriteMovies(): List<Movie>
    suspend fun getFavoriteTVs(): List<TVShow>

    suspend fun getRatedMovies(): List<Movie>
    suspend fun getRatedTVs(): List<TVShow>

    suspend fun getWatchlistMovies(): List<Movie>
    suspend fun getWatchlistTVs(): List<TVShow>

    suspend fun getFavorites(): List<MediaItem>
    suspend fun getRated(): List<MediaItem>
    suspend fun getWatchlist(): List<MediaItem>
}