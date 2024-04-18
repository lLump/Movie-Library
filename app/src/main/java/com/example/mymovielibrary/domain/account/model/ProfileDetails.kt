package com.example.mymovielibrary.domain.account.model

import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay

data class ProfileDetails(
    val name: String,
    val username: String,
    val avatarPath: String,
    val languageIso: String,
)