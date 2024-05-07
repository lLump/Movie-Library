package com.example.mymovielibrary.data.auth.repository

import android.util.Log
import com.example.mymovielibrary.data.storage.TAG
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import okhttp3.MediaType
import okhttp3.RequestBody

class AuthRepoImpl(private val api: AuthApi) : AuthRepository {

//    private fun getErrorOnCode(code: Int): DataError.Network {
//        return when (code) {
//            400 -> DataError.Network.BAD_REQUEST
//            401 -> DataError.Network.UNAUTHORIZED
//            403 -> DataError.Network.FORBIDDEN
//            500 -> DataError.Network.SERVER_ERROR
//            503 -> DataError.Network.SERVICE_UNAVAILABLE
//            else -> DataError.Network.UNKNOWN
//        }
//    }

    override suspend fun createRequestTokenV4(): Result<String, DataError.Network> {
        return try {
            val mediaType = MediaType.parse("application/json")
            val body = RequestBody.create(
                mediaType,
                "{\"redirect_to\":\"https://www.example.mymovielibrary:\"}"
            )
//            val mediaType = "application/json".toMediaType()
//            val body = "{\"redirect_to\":\"https://www.example.mymovielibrary:\"}".toRequestBody(mediaType)

            val response = api.createRequestTokenV4(body)
            if (!response.success) throw Exception("Token request failed")

            Result.Success(response.request_token)
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message ?: "Token request failed"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Token request failed"))
        }
    }

    override suspend fun createAccessTokenV4(requestToken: String): Result<Pair<String, String>, DataError.Network> {
        return try {
            val mediaType = MediaType.parse("application/json")
            val body =
                RequestBody.create(mediaType, "{\"request_token\":\"${requestToken}\"}")
//            val mediaType = "application/json".toMediaType()
//            val body = "{\"request_token\":\"${requestToken}\"}".toRequestBody(mediaType)

            val response = api.createAccessTokenV4(body)
            if (!response.success) throw Exception("Session creation failed")

            Result.Success(Pair(response.account_id, response.access_token))
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message ?: "Session creation failed"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Session creation failed"))
        }
    }

    override suspend fun getSessionIdV4(accessToken: String): Result<String, DataError.Network> {
        return try {
            val mediaType = MediaType.parse("application/json")
            val body = RequestBody.create(mediaType, "{\"access_token\":\"${accessToken}\"}")
//            val mediaType = "application/json".toMediaType()
//            val body = "{\"access_token\":\"${accessToken}\"}".toRequestBody(mediaType)

            val response = api.createSessionWithV4Token(body)
            if (!response.success) throw Exception("Session creation failed")

            Result.Success(response.session_id)
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message ?: "Session creation failed"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Session creation failed"))
        }
    }

    override suspend fun getGuestSessionId(): Result<String, DataError.Network> {
        return try {
            val response = api.createGuestSession()
            if (!response.success) throw Exception("Guest session wasn't created")

            Result.Success(response.guest_session_id)
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message ?: "Guest login currently unavailable"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Guest login currently unavailable"))
        }
    }

}