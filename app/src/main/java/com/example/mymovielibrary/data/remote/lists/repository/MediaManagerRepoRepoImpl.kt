package com.example.mymovielibrary.data.remote.lists.repository

import com.example.mymovielibrary.data.remote.lists.api.MediaManagerApi
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.repository.MediaManagerRepo
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class MediaManagerRepoRepoImpl(private val api: MediaManagerApi): MediaManagerRepo, BaseRepository() {
    private val accessToken: String
        get() = "Bearer ${Store.tmdbData.accessToken}"

    override suspend fun addOrDeleteInFavorite(
        mediaId: Int,
        isMovie: Boolean,
        isAdding: Boolean
    ): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Adding or deleting in Favorite unsuccessful") {
            val mediaType = "application/json".toMediaType()
            val movieType = if (isMovie) "movie" else "tv"
            val body = "{\"media_type\":\"$movieType\",\"media_id\":$mediaId,\"favorite\":$isAdding}".toRequestBody(mediaType)

            val response = api.addOrDeleteInFavorite(
                accountIdV3 = Store.tmdbData.accountIdV3.toString(),
                requestBody = body,
            )

            response.success
        }
    }

    override suspend fun addOrDeleteInWatchlist(
        mediaId: Int,
        isMovie: Boolean,
        isAdding: Boolean
    ): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Adding or deleting in Watchlist unsuccessful") {
            val mediaType = "application/json".toMediaType()
            val movieType = if (isMovie) "movie" else "tv"
            val body = "{\"media_type\":\"$movieType\",\"media_id\":$mediaId,\"watchlist\":$isAdding}".toRequestBody(mediaType)

            val response = api.addOrDeleteInWatchlist(
                accountIdV3 = Store.tmdbData.accountIdV3.toString(),
                requestBody = body,
            )

            response.success
        }
    }

    override suspend fun addOrDeleteRating(
        mediaId: Int,
        isMovie: Boolean,
        isAdding: Boolean
    ): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Adding or deleting Rating unsuccessful") {
            val mediaType = "application/json".toMediaType()
            val movieType = if (isMovie) "movie" else "tv"
            val body = "{\"media_type\":\"$movieType\",\"media_id\":$mediaId,\"watchlist\":$isAdding}".toRequestBody(mediaType)

//            val response = api.addOrDeleteInFavorite(
//                accountIdV3 = TmdbData.accountIdV3.toString(),
//                requestBody = body,
//            )

//            response.success
            false //TODO (закончить add/delete rating)
        }
    }

    override suspend fun addMediasToCollection(
        collectionId: Int,
        jsonBody: String,
    ): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection add mediaItems Failed") {
            val mediaType = "application/json".toMediaType()
            val body = jsonBody.toRequestBody(mediaType)

            val response = api.addItemsToCollection(collectionId, body, accessToken)

            response.success
        }
    }

    override suspend fun checkIfMediaInCollection(
        collectionId: Int,
        mediaId: Int,
        mediaType: String
    ): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "MediaItem check in collection Failed") {
            val response = api.checkIfItemInCollection(collectionId, mediaId, mediaType)

            response.success
        }
    }
}