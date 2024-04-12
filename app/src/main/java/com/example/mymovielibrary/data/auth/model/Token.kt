package com.example.mymovielibrary.data.auth.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)