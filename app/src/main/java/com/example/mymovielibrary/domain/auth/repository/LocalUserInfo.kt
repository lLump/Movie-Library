package com.example.mymovielibrary.domain.auth.repository


interface LocalUserInfo {
    fun saveUserInfo(accountIdV4: String, sessionId: String)
    fun getInfoIfExist(info: (Boolean, String, String) -> Unit)
    fun clearInfo()
}