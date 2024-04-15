package com.example.mymovielibrary.data.account.api

import com.example.mymovielibrary.data.account.model.AccountDetails
import com.example.mymovielibrary.data.account.model.LanguageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountApi {

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): AccountDetails

    @GET("configuration/languages")
    suspend fun getLanguages(): Array<LanguageResponse>

}