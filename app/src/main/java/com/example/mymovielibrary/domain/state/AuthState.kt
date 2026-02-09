package com.example.mymovielibrary.domain.state

sealed interface AuthState {
    data object Loading : AuthState
    data object Guest : AuthState
    data object Authorized : AuthState
    data object FromAuthorize : AuthState
}