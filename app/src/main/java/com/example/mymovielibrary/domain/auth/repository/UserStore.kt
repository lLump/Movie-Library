package com.example.mymovielibrary.domain.auth.repository

import com.example.mymovielibrary.domain.auth.model.UserInfo

interface UserStore {
    var sessionId: String
    var requestToken: String

    fun saveUserCredentials(user: UserInfo)
    fun getUserIfSaved(saved: (Pair<Boolean, UserInfo>) -> Unit)
}