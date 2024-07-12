package com.example.mymovielibrary.data.lists.helper

import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.helper.CollectionHelper
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.SortType
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManager

class CollectionHelperImpl(
    private val mediaRepo: CollectionRepo,
    private val mediaManager: MediaManager,
): CollectionHelper, BaseHelper() {
    override suspend fun getCollectionDetails(collectionId: Int): CollectionDetails? {
        return request { mediaRepo.getCollectionDetails(collectionId) }
    }

    override suspend fun updateCollectionInfo(name: String, description: String, public: Boolean, collectionId: Int): Boolean {
        return request { mediaRepo.updateCollectionInfo(name, description, public, collectionId) } ?: false
    }
    override suspend fun updateCollectionSortType(sortType: SortType, collectionId: Int): Boolean {
        return request { mediaRepo.updateCollectionSortType(
            sortBy = sortType,
            collectionId = collectionId
        ) } ?: false
    }
    override suspend fun updateCollectionBackgroundPhoto(backdropPath: String, collectionId: Int): Boolean {
        return request { mediaRepo.updateCollectionBackgroundPhoto(
            backdropPath = backdropPath,
            collectionId = collectionId
        ) } ?: false
    }

    override suspend fun deleteItemsInCollection(collectionId: Int, itemsBody: String): Boolean {
        return request { mediaRepo.deleteItemInCollection(collectionId, itemsBody) } ?: false
    }

    override suspend fun addOrDeleteInWatchlist(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean {
        return request { mediaManager.addOrDeleteInWatchlist(mediaId, isMovie, isAdding) } ?: false
    }

    override suspend fun addOrDeleteInFavorite(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean {
        return request { mediaManager.addOrDeleteInFavorite(mediaId, isMovie, isAdding) } ?: false
    }

    override suspend fun addOrDeleteRating(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean {
        return request { mediaManager.addOrDeleteInFavorite(mediaId, isMovie, isAdding) } ?: false

    }

}