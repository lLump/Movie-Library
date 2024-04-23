package com.example.mymovielibrary.domain.lists.repository

import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.http.GET

interface ListRepository {

    @GET("3/account/{account_id}/lists")
    suspend fun getUserCollections(): Result<List<UserCollection>, DataError.Network>



}