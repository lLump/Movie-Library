package com.example.mymovielibrary.data.remote.auth.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionResponse(
    val success: Boolean,
    val session_id: String
)

@JsonClass(generateAdapter = true)
data class SessionGuestResponse(
    val success: Boolean,
    val guest_session_id: String,
    val expires_at: String
)

@JsonClass(generateAdapter = true)
data class ValidateSessionResponse(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)

