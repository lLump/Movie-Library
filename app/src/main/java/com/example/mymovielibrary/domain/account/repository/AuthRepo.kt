package com.example.mymovielibrary.domain.account.repository

import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface AuthRepo {
    suspend fun createRequestTokenV4(): Result<String, DataError>
    suspend fun createAccessTokenV4(requestToken: String): Result<Pair<String, String>, DataError>
    suspend fun getSessionIdV4(accessToken: String): Result<String, DataError>
    suspend fun getGuestSessionId(): Result<String, DataError>
//    suspend fun logout(): Result<Boolean, DataError>
    }