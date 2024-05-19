package com.example.mymovielibrary.domain.base.repository

import android.util.Log
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(errorMessage: String, request: suspend () -> T): Result<T, DataError> {
        return try {
            Result.Success(request.invoke())
        } catch (e: HttpException) {
            e.printStackTrace()

            Result.Error(getErrorOnCode(e.code()))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("API_CALL", e.stackTraceToString())
            Result.Error(DataError.Message(e.message ?: errorMessage))
        }
    }

    private fun getErrorOnCode(code: Int): DataError {
        return when (code) {
            400 -> DataError.Network.BAD_REQUEST
            401 -> DataError.Network.UNAUTHORIZED
            403 -> DataError.Network.FORBIDDEN
            500 -> DataError.Network.SERVER_ERROR
            503 -> DataError.Network.SERVICE_UNAVAILABLE
            else -> DataError.Network.UNKNOWN
        }
    }
}