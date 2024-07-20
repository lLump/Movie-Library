package com.example.mymovielibrary.domain.lists.repository

import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.enums.SortType
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface CollectionRepo {
    suspend fun getCollectionDetails(listId: Int): Result<CollectionDetails, DataError>
    suspend fun updateCollectionInfo(name: String, description: String, public: Boolean, collectionId: Int): Result<Boolean, DataError>
    suspend fun updateCollectionSortType(sortBy: SortType, collectionId: Int): Result<Boolean, DataError>
    suspend fun updateCollectionBackgroundPhoto(backdropPath: String, collectionId: Int): Result<Boolean, DataError>
    suspend fun deleteItemInCollection(collectionId: Int, itemsBody: String): Result<Boolean, DataError>
    suspend fun clearCollection(collectionId: Int): Result<Boolean, DataError>
    suspend fun deleteCollection(collectionId: Int): Result<Boolean, DataError>
    suspend fun createCollection(name: String, description: String, isPublic: Boolean): Result<Boolean, DataError>
}