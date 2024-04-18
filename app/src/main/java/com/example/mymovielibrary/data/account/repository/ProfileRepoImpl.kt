package com.example.mymovielibrary.data.account.repository

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.model.toLanguageDetails
import com.example.mymovielibrary.data.account.model.toProfileDetails
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.account.repository.ProfileRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepoImpl(private val api: AccountApi) : ProfileRepository {
    override suspend fun getLanguages(): Result<List<LanguageDetails>, DataError.Network> {
        return try {
            val response = api.getLanguages()

            Result.Success(response.map { it.toLanguageDetails() })
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        }
    }

    override suspend fun getProfileDetails(): Result<ProfileDetails, DataError.Network> {
        return try {
            val response = api.getAccountDetails(TmdbData.accountId.toString())

            Result.Success(response.toProfileDetails())
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting languages"))
        }
    }


}