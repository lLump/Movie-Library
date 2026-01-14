package com.example.mymovielibrary.data.remote.lists.api

import com.example.mymovielibrary.data.remote.auth.model.DefaultResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaManagerApi {

    @POST("4/list/{list_id}/items")
    suspend fun addItemsToCollection(
        @Path("list_id") listId: Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") accessToken: String,
        @Header("content-type") type: String = "application/json"
    ): DefaultResponse
    // в ответе так же приходит массив, с success: Boolean для каждого mediaItem
    // что не предусмотрено здесь ↑, и не уверен нужно ли

    @GET("4/list/{list_id}/item_status")
    suspend fun checkIfItemInCollection(
        @Path("list_id") listId: Int,
        @Query("media_id") mediaId: Int,
        @Query("media_type") mediaType: String
    ): DefaultResponse

    @POST("3/account/{account_id}/favorite")
    suspend fun addOrDeleteInFavorite(
        @Path("account_id") accountIdV3: String,
        @Body requestBody: RequestBody,
    ): DefaultResponse

    @POST("3/account/{account_id}/watchlist")
    suspend fun addOrDeleteInWatchlist(
        @Path("account_id") accountIdV3: String,
        @Body requestBody: RequestBody,
    ): DefaultResponse
}