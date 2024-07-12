package com.example.mymovielibrary.data.auth.repository

import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepoImpl(private val api: AuthApi) : BaseRepository() {

    suspend fun createRequestTokenV4(): Result<String, DataError> {
        return safeApiCall("Request Token create failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"redirect_to\":\"https://www.example.mymovielibrary:\"}".toRequestBody(mediaType)

            val response = api.createRequestTokenV4(body)
            if (!response.success) throw Exception("Request Token create EXCEPTION")

            response.request_token
        }
    }

    suspend fun createAccessTokenV4(requestToken: String): Result<Pair<String, String>, DataError> {
        return safeApiCall("Request AccessToken create failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"request_token\":\"${requestToken}\"}".toRequestBody(mediaType)

            val response = api.createAccessTokenV4(body)
            if (!response.success) throw Exception("Request AccessToken create EXCEPTION")

            Pair(response.account_id, response.access_token)
        }
    }

    suspend fun logout(): Result<Boolean, DataError> {
        return safeApiCall("Logout Error") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"access_token\":\"${TmdbData.accessToken}\"}".toRequestBody(mediaType)

            val response = api.logout(body)

            response.success
        }
    }

    suspend fun getSessionIdV4(accessToken: String): Result<String, DataError> {
        return safeApiCall("Request SessionID failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"access_token\":\"${accessToken}\"}".toRequestBody(mediaType)

            val response = api.createSessionWithV4Token(body)
            if (!response.success) throw Exception("Request SessionID EXCEPTION")

            response.session_id
        }
    }

    suspend fun getGuestSessionId(): Result<String, DataError> {
        return safeApiCall("Request Guest sessionId failed") {
            val response = api.createGuestSession()
            if (!response.success) throw Exception("Request Guest sessionId EXCEPTION")

            response.guest_session_id
        }
    }
}