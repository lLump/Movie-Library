package com.example.mymovielibrary.data.remote.auth.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DefaultResponse(
    val status_message: String,
    val success: Boolean,
    val status_code: Int
)