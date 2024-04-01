package com.example.mymovielibrary.domain.auth.repository

import com.example.mymovielibrary.domain.auth.model.UserInfo
import com.example.mymovielibrary.presentation.model.UiEventListener //FIXME

interface AuthHelper {
    fun setEventListener(listener: UiEventListener) //FIXME
    fun getStartScreen(): String

    fun guestLogin()
    fun performLogin(user: UserInfo, needToSave: Boolean)
}