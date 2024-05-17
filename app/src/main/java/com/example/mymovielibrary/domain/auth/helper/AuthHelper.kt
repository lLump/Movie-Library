package com.example.mymovielibrary.domain.auth.helper

interface AuthHelper {
    suspend fun getRequestToken(): String
    suspend fun finishAuth(requestToken: String)
    suspend fun logout()
}