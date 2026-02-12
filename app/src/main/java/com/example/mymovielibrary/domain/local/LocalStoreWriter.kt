package com.example.mymovielibrary.domain.local

interface LocalStoreWriter {
    fun clearInfo()
    fun saveTempRequestToken(token: String)
    fun saveAccountIdV3(id: Int)
    fun saveUserInfo(accountIdV4: String, sessionId: String, accessToken: String)
    fun saveNewResponseLanguage(iso639: String)
}