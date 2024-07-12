package com.example.mymovielibrary.data.lists.helper

import com.example.mymovielibrary.data.lists.repository.ListRepoImpl
import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.helper.DetailListHelper
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.sortedByTitle
import com.example.mymovielibrary.domain.lists.repository.MediaManager

class DetailListHelperImpl(
    private val listRepo: ListRepoImpl,
    private val mediaManager: MediaManager
): DetailListHelper, BaseHelper() {
    override suspend fun getFavorites(): List<MediaItem> {
        val favMovies = request { listRepo.getFavoriteMovies() } ?: return emptyList()
        val favTvs = request { listRepo.getFavoriteTvShows() } ?: return emptyList()
        return (favMovies + favTvs).sortedByTitle()
    }

    override suspend fun getRated(): List<MediaItem> {
        val ratedMovies = request { listRepo.getRatedMovies() } ?: return emptyList()
        val ratedTvs = request { listRepo.getRatedTvShows() } ?: return emptyList()
        return (ratedMovies + ratedTvs).sortedByTitle()
    }

    override suspend fun getWatchlist(): List<MediaItem> {
        val watchMovies = request { listRepo.getWatchlistMovies() } ?: return emptyList()
        val watchTvs = request { listRepo.getWatchlistTvShows() } ?: return emptyList()
        return (watchMovies + watchTvs).sortedByTitle()
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