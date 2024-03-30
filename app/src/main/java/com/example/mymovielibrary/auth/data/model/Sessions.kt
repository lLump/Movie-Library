package com.example.mymovielibrary.auth.data.model

data class SessionResponse(
    val success: Boolean,
    val session_id: String
)

data class SessionGuestResponse(
    val success: Boolean,
    val guest_session_id: String,
    val expires_at: String
)

data class ValidateSessionResponse(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)

