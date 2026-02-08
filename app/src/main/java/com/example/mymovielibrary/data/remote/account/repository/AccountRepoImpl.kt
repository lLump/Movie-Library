package com.example.mymovielibrary.data.remote.account.repository

import com.example.mymovielibrary.data.remote.account.api.AccountApi
import com.example.mymovielibrary.data.remote.account.model.toLanguageDetails
import com.example.mymovielibrary.data.remote.account.model.toProfileDetails
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class AccountRepoImpl(private val api: AccountApi, localStore: LocalStoreReader) : AccountRepo, BaseRepository(localStore) {
    override suspend fun getProfileDetails(): Result<ProfileDetails, DataError> {
        return safeApiCall("Request ProfileDetails failed") {
            val response = api.getAccountDetails(localStore.sessionId ?: throw Exception("No sessionId provided in getProfileDetails"))

            response.toProfileDetails()
        }
    }

    override suspend fun getLanguages(): Result<List<LanguageDetails>, DataError> {
        return safeApiCall("Getting languages failed") {
            val response = api.getLanguages()

            response.map { it.toLanguageDetails() }
        }
    }

    override suspend fun logout(): Result<Boolean, DataError> {
        return safeApiCall("Logout Error") {
            val mediaType = "application/json".toMediaType()
            val body = "{\"access_token\":\$accessToken\"}".toRequestBody(mediaType)

            val response = api.logout(body)

            response.success
        }
    }
}