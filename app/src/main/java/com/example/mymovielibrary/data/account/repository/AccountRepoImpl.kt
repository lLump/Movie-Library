package com.example.mymovielibrary.data.account.repository

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.model.toLanguageDetails
import com.example.mymovielibrary.data.account.model.toProfileDetails
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

class AccountRepoImpl(private val api: AccountApi) : AccountRepository, BaseRepository() {

    override suspend fun getLanguages(): Result<List<LanguageDetails>, DataError> {
        return safeApiCall("Getting languages failed") {
            val response = api.getLanguages()

            response.map { it.toLanguageDetails() }
        }
    }

    override suspend fun getProfileDetails(sessionId: String): Result<ProfileDetails, DataError> {
        return safeApiCall("Request ProfileDetails failed") {
            val response = api.getAccountDetails(sessionId)

            response.toProfileDetails()
        }
    }
}