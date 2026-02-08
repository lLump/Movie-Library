package com.example.mymovielibrary.domain.account.repository

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface AccountRepo {
    suspend fun getProfileDetails(): Result<ProfileDetails, DataError>
    suspend fun getLanguages(): Result<List<LanguageDetails>, DataError>

    suspend fun logout(): Result<Boolean, DataError>

}