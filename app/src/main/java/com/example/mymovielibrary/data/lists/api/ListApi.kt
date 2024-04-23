package com.example.mymovielibrary.data.lists.api

import com.example.mymovielibrary.data.lists.model.UserListsResponse
import com.example.mymovielibrary.data.storage.TmdbData
import retrofit2.http.GET
import retrofit2.http.Path

interface ListApi {

    @GET("3/account/{account_id}/lists")
    suspend fun getUserLists(@Path("account_id") accountId: String = TmdbData.accountId.toString()): UserListsResponse

}