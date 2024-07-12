package com.example.mymovielibrary.domain.lists.helper

import com.example.mymovielibrary.domain.lists.model.MediaItem

interface DetailListHelper {
    suspend fun getFavorites(): List<MediaItem>
    suspend fun getRated(): List<MediaItem>
    suspend fun getWatchlist(): List<MediaItem>

    suspend fun addOrDeleteInWatchlist(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean
    suspend fun addOrDeleteInFavorite(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean
    suspend fun addOrDeleteRating(mediaId: Int, isMovie: Boolean, isAdding: Boolean): Boolean
}