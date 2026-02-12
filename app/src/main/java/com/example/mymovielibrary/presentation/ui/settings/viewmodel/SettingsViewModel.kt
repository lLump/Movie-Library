package com.example.mymovielibrary.presentation.ui.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.model.events.SettingsEvent
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeCountry
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeResponseLanguage
import com.example.mymovielibrary.domain.model.events.SettingsEvent.CollectionsToStatistics
import com.example.mymovielibrary.domain.model.events.SettingsEvent.SaveLanguage
import com.example.mymovielibrary.domain.model.events.SettingsEvent.Logout
import com.example.mymovielibrary.presentation.ui.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountRepo: AccountRepo,
    private val localStore: LocalStoreWriter
): BaseViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is ChangeCountry -> {}
            is ChangeResponseLanguage -> saveNewLanguage(event.language)
            is CollectionsToStatistics -> {}
            is SaveLanguage -> { }
            Logout -> { }
        }
    }

    private fun saveNewLanguage(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val iso639 = getIsoByName(language)
            localStore.saveNewResponseLanguage(iso639)
//            val iso639 = language.substringBefore("-")
//            val iso3166 = language.substringAfter("-")
        }
    }

    private fun changeCountry(country: String) {

    }

    private suspend fun logout() {
        accountRepo.logout()
    }

    private fun getIsoByName(name: String): String {
        return getLanguages().find { it.name == name }!!.iso
    }

    private fun getLanguages(): List<LanguageDetails> { // im too lazy to get that from the server fixme
        return listOf(
            LanguageDetails(name = "English", iso = "en"),
            LanguageDetails(name = "Русский", iso = "ru"),
            LanguageDetails(name = "Українська", iso = "uk"),
            LanguageDetails(name = "Deutsch", iso = "de"),
            LanguageDetails(name = "Français", iso = "fr"),
            LanguageDetails(name = "Český", iso = "cs")
        )
    }
}