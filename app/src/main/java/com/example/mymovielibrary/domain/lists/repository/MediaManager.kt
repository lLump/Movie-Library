package com.example.mymovielibrary.domain.lists.repository

import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

interface MediaManager {
    suspend fun addOrDeleteInWatchlist(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Result<Boolean, DataError>
    suspend fun addOrDeleteInFavorite(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Result<Boolean, DataError>
    suspend fun addOrDeleteRating(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Result<Boolean, DataError>
    suspend fun addMediasToCollection(collectionId: Int, jsonBody: String): Result<Boolean, DataError>
    suspend fun checkIfMediaInCollection(collectionId: Int, mediaId: Int, mediaType: String): Result<Boolean, DataError>
}