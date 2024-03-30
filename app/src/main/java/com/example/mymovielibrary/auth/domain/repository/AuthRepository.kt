package com.example.mymovielibrary.auth.domain.repository

import com.example.mymovielibrary.core.domain.model.DataError
import com.example.mymovielibrary.core.domain.model.Result

interface AuthRepository {

    suspend fun getToken(): Result<String, DataError.Network>

    suspend fun createGuestSession(): Result<String, DataError.Network>

    suspend fun createSession(token: String): Result<String, DataError.Network>

    suspend fun validateToken(token: String, username: String, password: String): Result<Boolean, DataError.Network>

}