package com.example.mymovielibrary.domain.auth.helper

import com.example.mymovielibrary.domain.auth.model.UserInfo

interface AuthHelper {
    fun getStartScreen(): String

    fun guestLogin()
    fun performLogin(user: UserInfo, needToSave: Boolean)
}