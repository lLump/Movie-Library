package com.example.mymovielibrary.data.account.repository

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.model.toLanguageDetails
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(
    private val api: AccountApi
) : AccountRepository {
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
}