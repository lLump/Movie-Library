package com.example.mymovielibrary.data.remote.account.api

import com.example.mymovielibrary.data.remote.account.model.AccountDetailsResponse
import com.example.mymovielibrary.data.remote.account.model.LanguageResponse
import com.example.mymovielibrary.data.remote.auth.model.DefaultResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Query

interface AccountApi {

    @GET("3/account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String
    ): AccountDetailsResponse

    @GET("3/configuration/languages")
    suspend fun getLanguages(): Array<LanguageResponse>
}