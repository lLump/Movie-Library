package com.example.mymovielibrary.domain.auth.repository

import com.example.mymovielibrary.presentation.model.UiEventListener //FIXME

interface AuthHelper {
    fun setEventListener(listener: UiEventListener) //FIXME
    fun getStartScreen(): String

    fun guestLogin()
    fun login(login: String, password: String, needToSave: Boolean)
}