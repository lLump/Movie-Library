package com.example.mymovielibrary.data.auth.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenV3(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)

@JsonClass(generateAdapter = true)
data class RequestTokenV4(
    val status_message: String,
    val request_token: String,
    val success: Boolean,
    val status_code: Int
)

@JsonClass(generateAdapter = true)
data class AccessTokenV4(
    val account_id: String,
    val access_token: String,
    val success: Boolean,
    val status_message: String,
    val status_code: Int
)