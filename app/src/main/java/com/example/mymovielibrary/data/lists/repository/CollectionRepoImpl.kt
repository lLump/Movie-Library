package com.example.mymovielibrary.data.lists.repository

import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.model.collection.toCollectionDetails
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.SortType
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class CollectionRepoImpl(private val api: ListApi) : CollectionRepo, BaseRepository() {
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
                token = "Bearer ${TmdbData.accessToken}"
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
                token = "Bearer ${TmdbData.accessToken}"
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
                token = "Bearer ${TmdbData.accessToken}"
            )

            response.success
        }
    }

    override suspend fun deleteItemInCollection(collectionId: Int, itemsBody: String): Result<Boolean, DataError> {
        return safeApiCall(errorMessage = "Collection items delete Failed") {
            val mediaType = "application/json".toMediaType()
            val body = itemsBody.toRequestBody(mediaType)

            val response = api.deleteItemsInCollection(collectionId, body, "Bearer ${TmdbData.accessToken}")

            response.success
        }
    }

}