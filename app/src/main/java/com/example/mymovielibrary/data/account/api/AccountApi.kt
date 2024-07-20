package com.example.mymovielibrary.data.account.api

import com.example.mymovielibrary.data.account.model.AccountDetailsResponse
import com.example.mymovielibrary.data.account.model.LanguageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountApi {

    @GET("3/account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String
    ): AccountDetailsResponse

    //fixme вынести отсюда
    @GET("3/configuration/languages")
    suspend fun getLanguages(): Array<LanguageResponse>

}