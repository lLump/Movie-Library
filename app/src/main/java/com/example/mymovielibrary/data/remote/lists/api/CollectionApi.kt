package com.example.mymovielibrary.data.remote.lists.api

import com.example.mymovielibrary.data.remote.auth.model.DefaultResponse
import com.example.mymovielibrary.data.remote.lists.model.collection.CollectionDetailsResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionApi {

    @GET("4/list/{list_id}")
    suspend fun getCollectionDetails(@Path("list_id") listId: Int): CollectionDetailsResponse

    @PUT("4/list/{list_id}")
    suspend fun updateCollectionInfo(
        @Path("list_id") listId: Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") token: String,
//        @Header("content-type") type: String = "application/json",
    ): DefaultResponse

    @HTTP(method = "DELETE", path = "4/list/{list_id}/items", hasBody = true)
    suspend fun deleteItemsInCollection(
        @Path("list_id") listId: Int,
        @Body requestBody: RequestBody,
        @Header("Authorization") accessToken: String,
        @Header("content-type") type: String = "application/json",
    ): DefaultResponse

    @GET("4/list/{list_id}/clear")
    suspend fun clearCollection(
        @Path("list_id") listId: Int,
        @Header("Authorization") token: String
    ): DefaultResponse

//    @HTTP(method = "DELETE", path = "4/{list_id}", hasBody = false)
//    suspend fun deleteCollection(
//        @Path("list_id") listId: Int,
//        @Header("Authorization") token: String,
//    ): DefaultResponse

    @DELETE("3/list/{list_id}")
    suspend fun deleteCollection(
        @Path("list_id") listId: Int,
        @Query("session_id") sessionId: String,
        @Header("Authorization") token: String,
    ): DefaultResponse

    @POST("4/list")
    suspend fun createCollection(
        @Body body: RequestBody,
        @Header("Authorization") accessToken: String
    ): DefaultResponse
}