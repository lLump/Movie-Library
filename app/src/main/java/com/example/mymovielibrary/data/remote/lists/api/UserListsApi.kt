package com.example.mymovielibrary.data.remote.lists.api

import com.example.mymovielibrary.data.remote.lists.model.collection.UserCollectionsResponse
import com.example.mymovielibrary.data.remote.lists.model.media.MoviesResponse
import com.example.mymovielibrary.data.remote.lists.model.media.TVShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserListsApi {
    // Collections
    @GET("4/account/{account_object_id}/lists")
    suspend fun getUserCollections(@Path("account_object_id") accountId: String): UserCollectionsResponse
    // Collections

    // Favorites
    @GET("4/account/{account_object_id}/movie/favorites")
    suspend fun getFavoriteMovies(@Path("account_object_id") accountId: String): MoviesResponse

    @GET("4/account/{account_object_id}/tv/favorites")
    suspend fun getFavoriteTvShows(@Path("account_object_id") accountId: String): TVShowsResponse
    // Favorites

    // Rated
    @GET("4/account/{account_object_id}/movie/rated")
    suspend fun getRatedMovies(@Path("account_object_id") accountId: String): MoviesResponse

    @GET("4/account/{account_object_id}/tv/rated")
    suspend fun getRatedTvShows(@Path("account_object_id") accountId: String): TVShowsResponse
    // Rated

    // Watchlist
    @GET("4/account/{account_object_id}/movie/watchlist")
    suspend fun getWatchlistMovies(@Path("account_object_id") accountId: String): MoviesResponse

    @GET("4/account/{account_object_id}/tv/watchlist")
    suspend fun getWatchlistTvShows(@Path("account_object_id") accountId: String): TVShowsResponse
    // Watchlist
}