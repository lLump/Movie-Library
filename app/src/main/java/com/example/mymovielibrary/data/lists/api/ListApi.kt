package com.example.mymovielibrary.data.lists.api

import com.example.mymovielibrary.data.auth.model.DefaultAnswer
import com.example.mymovielibrary.data.lists.model.collection.CollectionDetailsResponse
import com.example.mymovielibrary.data.lists.model.collection.UserCollectionsResponse
import com.example.mymovielibrary.data.lists.model.media.MoviesResponse
import com.example.mymovielibrary.data.lists.model.media.TVShowsResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ListApi {
    // Collections
    @GET("4/account/{account_object_id}/lists")
    suspend fun getUserCollections(@Path("account_object_id") accountId: String): UserCollectionsResponse

    @GET("4/list/{list_id}")
    suspend fun getCollectionDetails(@Path("list_id") listId: Int): CollectionDetailsResponse

    @GET("4/list/{list_id}/item_status")
    suspend fun checkIfItemInCollection(
        @Path("list_id") listId: Int,
        @Query("media_id") mediaId: Int,
        @Query("media_type") mediaType: String
    ): DefaultAnswer

    @PUT("4/list/{list_id}")
    suspend fun updateCollectionInfo(
        @Path("list_id") listId: Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") token: String,
//        @Header("content-type") type: String = "application/json",
    ): DefaultAnswer

    @POST("4/list/{list_id}/items")
    suspend fun addItemsToCollection(
        @Path("list_id") listId: Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") accessToken: String,
        @Header("content-type") type: String = "application/json"
    ): DefaultAnswer
    // в ответе так же приходит массив, с success: Boolean для каждого mediaItem
    // что не предусмотрено здесь ↑, и не уверен нужно ли

    @HTTP(method = "DELETE", path = "4/list/{list_id}/items", hasBody = true)
    suspend fun deleteItemsInCollection(
        @Path("list_id") listId: Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") accessToken: String,
        @Header("content-type") type: String = "application/json",
    ): DefaultAnswer
    // Collections

    // Favorites
    @GET("4/account/{account_object_id}/movie/favorites")
    suspend fun getFavoriteMovies(@Path("account_object_id") accountId: String): MoviesResponse

    @GET("4/account/{account_object_id}/tv/favorites")
    suspend fun getFavoriteTvShows(@Path("account_object_id") accountId: String): TVShowsResponse

    @POST("3/account/{account_id}/favorite")
    suspend fun addOrDeleteInFavorite(
        @Path("account_id") accountIdV3: String,
        @Body requestBody: RequestBody,
    ): DefaultAnswer
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

    @POST("3/account/{account_id}/watchlist")
    suspend fun addOrDeleteInWatchlist(
        @Path("account_id") accountIdV3: String,
        @Body requestBody: RequestBody,
    ): DefaultAnswer
    // Watchlist
}