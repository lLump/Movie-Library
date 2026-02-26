package com.example.mymovielibrary.domain.local

import com.example.mymovielibrary.domain.account.model.UserCollectionInfo

interface SettingsWriter {
    fun clearInfo()
    fun saveApiResponseLanguage(languageName: String)
    fun saveUserCollections(collectionsInfo: List<UserCollectionInfo>)
    fun saveUserCollectionForStats(collectionInfo: UserCollectionInfo)
    fun removeUserCollectionFromStats(collectionInfo: UserCollectionInfo)
}