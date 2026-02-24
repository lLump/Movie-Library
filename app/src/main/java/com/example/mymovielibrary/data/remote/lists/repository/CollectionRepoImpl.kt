package com.example.mymovielibrary.data.remote.lists.repository

import com.example.mymovielibrary.data.remote.lists.api.CollectionManagerApi
import com.example.mymovielibrary.data.remote.lists.util.toCollectionDetails
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.enums.SortType
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class CollectionRepoImpl(private val api: CollectionManagerApi, private val settings: SettingsReader, localStore: LocalStoreReader) : CollectionRepo, BaseRepository(localStore) {
    private val accessToken: String
        get() = "Bearer ${localStore.accessToken}"

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
            val response = api.deleteCollection(
                collectionId,
                token = accessToken,
                sessionId = localStore.sessionId ?: throw Exception("No sessionId provided in deleteCollection")
            )

            response.success
        }
    }

    override suspend fun createCollection(name: String, description: String, isPublic: Boolean): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection creating Failed") {
            val mediaType = "application/json".toMediaType()
//            val body = "{\"description\":\"$description\",\"name\":\"$name\",\"iso_3166_1\":\"${localStore.iso3166}\",\"iso_639_1\":\"${localStore.iso639}\",\"public\":$isPublic}".toRequestBody(mediaType)
            val body = "{\"description\":\"$description\",\"name\":\"$name\",\"iso_3166_1\":\"US\",\"iso_639_1\":\"${settings.language.iso639}\",\"public\":$isPublic}".toRequestBody(mediaType)

            val response = api.createCollection(body, accessToken)
            response.success
        }
    }

}