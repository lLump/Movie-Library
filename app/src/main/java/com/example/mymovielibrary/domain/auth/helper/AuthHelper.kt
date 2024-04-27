package com.example.mymovielibrary.domain.auth.helper

interface AuthHelper {
    suspend fun getRequestToken(): String
    fun saveTmdbInfo(requestToken: String)
}