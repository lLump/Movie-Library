package com.example.mymovielibrary.domain.lists.helper

import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.SortType

interface CollectionHelper {
    suspend fun getCollectionDetails(collectionId: Int): CollectionDetails?
    suspend fun updateCollectionInfo(name: String, description: String, public: Boolean, collectionId: Int): Boolean
    suspend fun updateCollectionSortType(sortType: SortType, collectionId: Int): Boolean
    suspend fun updateCollectionBackgroundPhoto(backdropPath: String, collectionId: Int): Boolean
    suspend fun deleteItemsInCollection(collectionId: Int, itemsBody: String): Boolean

    suspend fun addOrDeleteInWatchlist(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean
    suspend fun addOrDeleteInFavorite(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean
    suspend fun addOrDeleteRating(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean
}