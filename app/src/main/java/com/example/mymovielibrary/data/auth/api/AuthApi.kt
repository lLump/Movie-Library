package com.example.mymovielibrary.data.auth.api

import com.example.mymovielibrary.data.auth.model.AccessTokenV4
import com.example.mymovielibrary.data.auth.model.DefaultAnswer
import com.example.mymovielibrary.data.auth.model.RequestTokenV4
import com.example.mymovielibrary.data.auth.model.SessionGuestResponse
import com.example.mymovielibrary.data.auth.model.SessionResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    // V4 Api Below
    @POST("4/auth/request_token")
    suspend fun createRequestTokenV4(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): RequestTokenV4

    @POST("4/auth/access_token")
    suspend fun createAccessTokenV4(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): AccessTokenV4

    @HTTP(method = "DELETE", path = "4/auth/access_token", hasBody = true)
    suspend fun logout(
        @Body requestBody: RequestBody,
    ): DefaultAnswer
    // ----------------------------------------- //

    // V3 API Below
    @POST("/3/authentication/session/convert/4")
    suspend fun createSessionWithV4Token(
        @Body requestBody: RequestBody,
        @Header("content_type") type: String = "application/json"
    ): SessionResponse

    @GET("3/authentication/guest_session/new")
    suspend fun createGuestSession(): SessionGuestResponse
    // ----------------------------------------- //

}