package com.example.mymovielibrary.auth.domain.repository

import com.example.mymovielibrary.auth.domain.model.UserInfo

interface UserStore {
    var sessionId: String
    var requestToken: String

    fun saveUserCredentials(user: UserInfo)
    fun getUserIfSaved(saved: (Pair<Boolean, UserInfo>) -> Unit)
}