package com.example.mymovielibrary.domain.account.repository

import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import com.example.mymovielibrary.domain.state.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    val authState: Flow<AuthState>

    suspend fun createRequestTokenV4(): Result<String, DataError>
    suspend fun createAccessTokenV4(requestToken: String): Result<Pair<String, String>, DataError>
    suspend fun getSessionIdV4(accessToken: String): Result<String, DataError>
    suspend fun getGuestSessionId(): Result<String, DataError>

    suspend fun authorizeUser()
}