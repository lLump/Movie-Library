package com.example.mymovielibrary.data.account.repository

import android.util.Log
import com.example.mymovielibrary.data.storage.TAG
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.model.toLanguageDetails
import com.example.mymovielibrary.data.account.model.toProfileDetails
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

class AccountRepoImpl(private val api: AccountApi) : AccountRepository {

    override suspend fun getLanguages(): Result<List<LanguageDetails>, DataError.Network> {
        return try {
            val response = api.getLanguages()

            Result.Success(response.map { it.toLanguageDetails() })
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        }
    }

    override suspend fun getProfileDetails(sessionId: String): Result<ProfileDetails, DataError.Network> {
        return try {
            val response = api.getAccountDetails(sessionId)

            Result.Success(response.toProfileDetails())
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        }
    }
}