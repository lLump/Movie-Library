package com.example.mymovielibrary.data.auth.api

import com.example.mymovielibrary.data.auth.model.SessionGuestResponse
import com.example.mymovielibrary.data.auth.model.SessionResponse
import com.example.mymovielibrary.data.auth.model.TokenV3
import com.example.mymovielibrary.data.auth.model.AccessTokenV4
import com.example.mymovielibrary.data.auth.model.RequestTokenV4
import com.example.mymovielibrary.data.auth.model.ValidateSessionResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @GET("3/authentication/token/new")
    suspend fun getRequestTokenV3(): TokenV3

//    @POST("4/auth/request_token")
//    suspend fun createRequestTokenV4(
//        @Body requestBody: RequestBody,
//        @Header("content_type") type: String = "application/json"
//    ): RequestTokenV4
//
//    @POST("4/auth/access_token")
//    suspend fun createAccessTokenV4(
//        @Body requestBody: RequestBody,
//        @Header("content_type") type: String = "application/json"
//    ): AccessTokenV4

    @POST("3/authentication/token/validate_with_login")
    suspend fun validateSession(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): ValidateSessionResponse

    @GET("3/authentication/guest_session/new")
    suspend fun createGuestSession(): SessionGuestResponse

    @POST("3/authentication/session/new")
    suspend fun createSession(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): SessionResponse


}