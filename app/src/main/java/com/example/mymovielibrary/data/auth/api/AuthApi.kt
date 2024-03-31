package com.example.mymovielibrary.data.auth.api

import com.example.mymovielibrary.data.auth.model.SessionGuestResponse
import com.example.mymovielibrary.data.auth.model.SessionResponse
import com.example.mymovielibrary.data.auth.model.Token
import com.example.mymovielibrary.data.auth.model.ValidateSessionResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @GET("token/new")
    suspend fun getRequestToken(): Token

    @POST("token/validate_with_login")
    suspend fun validateSession(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): ValidateSessionResponse

    @GET("guest_session/new")
    suspend fun createGuestSession(): SessionGuestResponse

    @POST("session/new")
    suspend fun createSession(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): SessionResponse

}