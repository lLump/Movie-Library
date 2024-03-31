package com.example.mymovielibrary.domain.auth.repository

import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

interface AuthRepository {

    suspend fun getToken(): Result<String, DataError.Network>

    suspend fun createGuestSession(): Result<String, DataError.Network>

    suspend fun createSession(token: String): Result<String, DataError.Network>

    suspend fun validateToken(token: String, username: String, password: String): Result<Boolean, DataError.Network>

}