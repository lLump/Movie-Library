package com.example.mymovielibrary.domain.model

interface Error

sealed interface DataError: Error {
    data class Network(val error: String): DataError
//    enum class Network: DataError {
//        BAD_REQUEST,
//        UNAUTHORIZED,
//        FORBIDDEN,
//        REQUEST_TIMEOUT,
//        TOO_MANY_REQUESTS,
//        SERVER_ERROR,
//        SERVICE_UNAVAILABLE,
//        UNKNOWN
//    }

    enum class Local: DataError {
        DISK_FULL,

    }
}