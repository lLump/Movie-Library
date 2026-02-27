package com.example.mymovielibrary.domain.local

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo

interface SettingsReader {
    val isAdult: Boolean
    val language: LanguageDetails
    val userCollections: List<UserCollectionInfo>
    val userCollectionsForStats: List<Int>
}