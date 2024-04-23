package com.example.mymovielibrary.data.auth.repository

import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.HttpException

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

    override suspend fun getTokenV3(): Result<String, DataError.Network> {
        return try {
            val response = api.getRequestTokenV3()
            if (!response.success) throw Exception("Token request failed")

            Result.Success(response.request_token)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Token request failed"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Token request failed"))
        }
    }

    override suspend fun validateToken(
        token: String,
        username: String,
        password: String
    ): Result<Boolean, DataError.Network> {
        return try {
            val mediaType = MediaType.parse("application/json")
            val body = RequestBody.create(mediaType,
                "{\"username\":\"$username\"," +
                        "\"password\":\"$password\"," +
                        "\"request_token\":\"$token\"}"
            )

            val response = api.validateSession(body)
            if (!response.success) throw Exception("Session creation with login failed")

            Result.Success(true)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Validation unsuccessful"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Validation unsuccessful"))
        }
    }

    override suspend fun getGuestSessionId(): Result<String, DataError.Network> {
        return try {
            val response = api.createGuestSession()
            if (!response.success) throw Exception("Guest session wasn't created")

            Result.Success(response.guest_session_id)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Guest login currently unavailable"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Guest login currently unavailable"))
        }
    }

    override suspend fun getSessionId(token: String): Result<String, DataError.Network> {
        return try {
            val mediaType = MediaType.parse("application/json")
            val body = RequestBody.create(mediaType, "{\"request_token\":\"$token\"}")

            val response = api.createSession(body)
            if (!response.success) throw Exception("Session creation failed")

            Result.Success(response.session_id)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Session creation failed"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message?: "Session creation failed"))
        }
    }

}