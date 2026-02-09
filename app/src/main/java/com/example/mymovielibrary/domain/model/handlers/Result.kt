package com.example.mymovielibrary.domain.model.handlers

sealed interface Result<out D, out E : DataError> {
    data class Success<out D, out E : DataError>(val data: D) : Result<D, E>
    data class Error<out D, out E : DataError>(val error: E) : Result<D, E>
}

fun <D, E : DataError> Result<D, E>.getOrThrow(): D =
    when (this) {
        is Result.Success -> data
        is Result.Error -> throw IllegalStateException(error.toString())
    }