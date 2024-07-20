package com.example.mymovielibrary.data.lists.repository

import com.example.mymovielibrary.data.lists.api.CollectionApi
import com.example.mymovielibrary.data.lists.model.collection.toCollectionDetails
import com.example.mymovielibrary.data.storage.Store
import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.enums.SortType
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class CollectionRepoImpl(private val api: CollectionApi) : CollectionRepo, BaseRepository() {
    private val accessToken: String
        get() = "Bearer ${Store.tmdbData.accessToken}"

    override suspend fun getCollectionDetails(listId: Int): Result<CollectionDetails, DataError> {
        return safeApiCall(errorMessage = "API Collection details ERROR") {
            val response = api.getCollectionDetails(listId)

            response.toCollectionDetails()
        }
    }

    override suspend fun updateCollectionInfo(name: String, description: String, public: Boolean, collectionId: Int): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection Update Failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"description\":\"${description}\",\"name\":\"${name}\",\"public\":\"${public}\"}".toRequestBody(mediaType)

            val response = api.updateCollectionInfo(
                listId = collectionId,
                requestBody = body,
                token = accessToken
            )
            response.success
        }
    }

    override suspend fun updateCollectionSortType(sortBy: SortType, collectionId: Int): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection sort_by Update Failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"sort_by\":\"${sortBy.type}\"}".toRequestBody(mediaType)

            val response = api.updateCollectionInfo(
                listId = collectionId,
                requestBody = body,
                token = accessToken
            )

            response.success
        }
    }

    override suspend fun updateCollectionBackgroundPhoto(backdropPath: String, collectionId: Int): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection backdropPath Update Failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"backdrop_path\":\"${backdropPath}\"}".toRequestBody(mediaType)

            val response = api.updateCollectionInfo(
                listId = collectionId,
                requestBody = body,
                token = accessToken
            )

            response.success
        }
    }

    override suspend fun deleteItemInCollection(collectionId: Int, itemsBody: String): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection items delete Failed") {
            val mediaType = "application/json".toMediaType()
            val body = itemsBody.toRequestBody(mediaType)

            val response = api.deleteItemsInCollection(collectionId, body, accessToken)

            response.success
        }
    }

    override suspend fun clearCollection(collectionId: Int): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection clear Failed") {
            val response = api.clearCollection(collectionId, accessToken)

            response.success
        }
    }

    override suspend fun deleteCollection(collectionId: Int): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection deleting unsuccessful") {
//            val response = api.deleteCollection(collectionId, "Bearer ${Store.tmdbData.accessToken}")
            val response = api.deleteCollection(
                collectionId,
                token = accessToken,
                sessionId = Store.tmdbData.sessionId
            )

            response.success
        }
    }

    override suspend fun createCollection(name: String, description: String, isPublic: Boolean): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection creating Failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"description\":\"$description\",\"name\":\"$name\",\"iso_3166_1\":\"${Store.tmdbData.iso3166}\",\"iso_639_1\":\"${Store.tmdbData.iso639}\",\"public\":$isPublic}".toRequestBody(mediaType)

            val response = api.createCollection(body, accessToken)
            response.success
        }
    }

}