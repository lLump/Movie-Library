package com.example.mymovielibrary.auth.domain.repository

interface AuthHelper {
    fun getStartScreen(): String

    fun guestLogin()
    fun login(login: String, password: String, needToSave: Boolean)
}