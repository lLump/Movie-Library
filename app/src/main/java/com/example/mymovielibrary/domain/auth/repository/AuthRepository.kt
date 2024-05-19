package com.example.mymovielibrary.domain.auth.repository

import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

interface AuthRepository {

    suspend fun createRequestTokenV4(): Result<String, DataError>
                                                                //Pair(account_id, request_token)
    suspend fun createAccessTokenV4(requestToken: String): Result<Pair<String, String>, DataError>

    suspend fun getGuestSessionId(): Result<String, DataError>

    suspend fun getSessionIdV4(accessToken: String): Result<String, DataError>

//    suspend fun getSessionIdV3(token: String): Result<String, DataError.Network>

//    suspend fun getTokenV3(): Result<String, DataError.Network>

//    suspend fun validateToken(token: String, username: String, password: String): Result<Boolean, DataError.Network>

}