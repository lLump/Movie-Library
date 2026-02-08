package com.example.mymovielibrary.domain.local

interface LocalInfoManager {
    fun clearInfo()
    fun saveUserInfo(accountIdV4: String, sessionId: String, accessToken: String)
}