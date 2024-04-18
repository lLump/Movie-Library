package com.example.mymovielibrary.data.lists.api

import com.example.mymovielibrary.data.lists.model.UserListsResponse
import retrofit2.http.GET

interface ListsApi {

    @GET("account/{account_id}/lists")
    suspend fun getUserLists(): UserListsResponse

}