package com.example.mymovielibrary.domain.account.model

data class ProfileDetails(
    val id: Int,
    val name: String,
    val username: String,
    val avatarPath: String?,
    val languageIso: String,
)