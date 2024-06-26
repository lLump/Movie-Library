package com.example.mymovielibrary.data.lists.helper

import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.model.Movie
import com.example.mymovielibrary.domain.lists.model.TVShow
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.model.sortedByTitle
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats

class ListHelperImpl(
    private val listRepo: ListRepository
): ListHelper, BaseHelper() {

    override suspend fun getUserCollections(): List<UserCollection> {
        return request { listRepo.getUserCollections() } ?: return emptyList()
    }

    override suspend fun getItemsInCollection(listId: Int): List<MediaItem> {
        return request { listRepo.getCollectionDetails(listId) } ?: return emptyList()
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return request { listRepo.getFavoriteMovies() } ?: return emptyList()
    }

    override suspend fun getFavoriteTVs(): List<TVShow> {
        return request { listRepo.getFavoriteTvShows() } ?: return emptyList()
    }

    override suspend fun getRatedMovies(): List<Movie> {
        return request { listRepo.getRatedMovies() } ?: return emptyList()
    }

    override suspend fun getRatedTVs(): List<TVShow> {
        return request { listRepo.getRatedTvShows() } ?: return emptyList()
    }

    override suspend fun getWatchlistMovies(): List<Movie> {
        return request { listRepo.getWatchlistMovies() } ?: return emptyList()
    }

    override suspend fun getWatchlistTVs(): List<TVShow> {
        return request { listRepo.getWatchlistTvShows() } ?: return emptyList()
    }

    override suspend fun getFavorites(): List<MediaItem> {
        val favorites = getFavoriteMovies() + getFavoriteTVs()
        return favorites.sortedByTitle()
    }

    override suspend fun getRated(): List<MediaItem> {
        val rated = getRatedMovies() + getRatedTVs()
        return rated.sortedByTitle()
    }

    override suspend fun getWatchlist(): List<MediaItem> {
        val watchlist = getWatchlistMovies() + getWatchlistTVs()
        return watchlist.sortedByTitle()
    }

    override suspend fun getUserStats(): UserStats {
        //TODO как вариант создать переменную в которую сейвить стату и сделать из ListHelper singleton
        val watched = 0
        val planned = getWatchlistMovies().size + getWatchlistTVs().size
        val rated = getRatedMovies().size + getRatedTVs().size
        val favorites = getFavoriteMovies().size + getFavoriteTVs().size

        return UserStats(
            watched = watched.toString(),
            planned = planned.toString(),
            rated = rated.toString(),
            favorite = favorites.toString()
        )
    }

}