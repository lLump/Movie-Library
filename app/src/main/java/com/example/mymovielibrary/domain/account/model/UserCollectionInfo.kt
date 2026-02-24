package com.example.mymovielibrary.domain.account.model

import kotlinx.serialization.Serializable

@Serializable
data class UserCollectionInfo(
    val id: Int,
    val name: String
)