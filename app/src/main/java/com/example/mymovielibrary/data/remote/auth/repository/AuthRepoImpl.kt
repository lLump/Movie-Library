package com.example.mymovielibrary.data.remote.auth.repository

import com.example.mymovielibrary.data.remote.auth.api.AuthApi
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import com.example.mymovielibrary.domain.state.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepoImpl(private val api: AuthApi, localStore: LocalStoreReader) : BaseRepository(localStore), AuthRepo {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    override val authState: Flow<AuthState> = _authState.asStateFlow()

    init {
        _authState.value = //restoreAuthState
            if (localStore.accessToken.isNullOrEmpty()) { // && localStore.sessionId.isNullOrEmpty() излишне
                AuthState.Guest
            } else AuthState.Authorized
    }

    override suspend fun createRequestTokenV4(): Result<String, DataError> {
        return safeApiCall("Request Token create failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"redirect_to\":\"mymovielibrary://callback\"}".toRequestBody(mediaType)

            val response = api.createRequestTokenV4(body)
            if (!response.success) throw Exception("Request Token create EXCEPTION")

            response.request_token
        }
    }

    override suspend fun createAccessTokenV4(requestToken: String): Result<Pair<String, String>, DataError> {
        return safeApiCall("Request AccessToken create failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"request_token\":\"${requestToken}\"}".toRequestBody(mediaType)

            val response = api.createAccessTokenV4(body)
            if (!response.success) throw Exception("Request AccessToken create EXCEPTION")

            Pair(response.account_id, response.access_token)
        }
    }

    override suspend fun getSessionIdV4(accessToken: String): Result<String, DataError> {
        return safeApiCall("Request SessionID failed") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"access_token\":\"${accessToken}\"}".toRequestBody(mediaType)

            val response = api.createSessionWithV4Token(body)
            if (!response.success) throw Exception("Request SessionID EXCEPTION")

            response.session_id
        }
    }

    override suspend fun getGuestSessionId(): Result<String, DataError> {
        return safeApiCall("Request Guest sessionId failed") {
            val response = api.createGuestSession()
            if (!response.success) throw Exception("Request Guest sessionId EXCEPTION")

            response.guest_session_id
        }
    }
    //нужно только для того что-бы юзера при логине (после сайта) закидывало в профиль (либо сделать какой-то отдельный экран)
    override suspend fun authorizeUser() { _authState.value = AuthState.FromAuthorize }

    override suspend fun logout(): Result<Boolean, DataError> {
        return safeApiCall("Logout Error") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"access_token\":\"${localStore.accessToken}\"}".toRequestBody(mediaType)

            val response = api.logout(body)

            response.success
        }
    }
}