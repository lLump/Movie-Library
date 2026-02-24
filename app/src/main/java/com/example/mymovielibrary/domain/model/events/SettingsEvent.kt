package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.domain.account.model.UserCollectionInfo

sealed interface SettingsEvent {
    // content of user Collections which is gonna count as watched
    data class CollectionsToStatistics(val userCollections: List<UserCollectionInfo>): SettingsEvent
    data class ChangeCountry(val country: String): SettingsEvent
    data class ChangeResponseLanguage(val language: String): SettingsEvent

    data object Logout : SettingsEvent
}