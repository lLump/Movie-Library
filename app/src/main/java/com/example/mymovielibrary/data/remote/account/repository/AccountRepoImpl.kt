package com.example.mymovielibrary.data.remote.account.repository

import com.example.mymovielibrary.data.remote.account.api.AccountApi
import com.example.mymovielibrary.data.remote.account.model.toLanguageDetails
import com.example.mymovielibrary.data.remote.account.model.toProfileDetails
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class AccountRepoImpl(private val api: AccountApi) : AccountRepo, BaseRepository() {

    override suspend fun getProfileDetails(sessionId: String): Result<ProfileDetails, DataError> {
        return safeApiCall("Request ProfileDetails failed") {
            val response = api.getAccountDetails(sessionId)

            response.toProfileDetails()
        }
    }

    override suspend fun getLanguages(): Result<List<LanguageDetails>, DataError> {
        return safeApiCall("Getting languages failed") {
            val response = api.getLanguages()

            response.map { it.toLanguageDetails() }
        }
    }
}