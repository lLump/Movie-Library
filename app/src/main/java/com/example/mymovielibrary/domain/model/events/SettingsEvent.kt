package com.example.mymovielibrary.domain.model.events

sealed interface SettingsEvent {
    // content of user Collections which is gonna count as watched
    data class CollectionsToStatistics(val ids: List<Int>): SettingsEvent //todo в профиль

    data class ChangeCountry(val country: String): SettingsEvent
    data class ChangeResponseLanguage(val language: String): SettingsEvent

    data object Logout : SettingsEvent
}