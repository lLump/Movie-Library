package com.example.mymovielibrary.data.lists.api

import com.example.mymovielibrary.data.lists.model.CollectionDetailsResponse
import com.example.mymovielibrary.data.lists.model.MoviesResponse
import com.example.mymovielibrary.data.lists.model.TVShowsResponse
import com.example.mymovielibrary.data.lists.model.UserCollectionsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ListApi {
    // Collections
    @GET("4/account/{account_object_id}/lists")
    suspend fun getUserCollections(@Path("account_object_id") accountId: String): UserCollectionsResponse

    @GET("4/list/{list_id}")
    suspend fun getCollectionDetails(@Path("list_id") listId: Int): CollectionDetailsResponse
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