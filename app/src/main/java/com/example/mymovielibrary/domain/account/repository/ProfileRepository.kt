package com.example.mymovielibrary.domain.account.repository

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

interface ProfileRepository {
    suspend fun getLanguages(): Result<List<LanguageDetails>, DataError.Network>
    suspend fun getProfileDetails(): Result<ProfileDetails, DataError.Network>
}