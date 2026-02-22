package com.example.mymovielibrary.presentation.ui.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.model.events.SettingsEvent
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeCountry
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeResponseLanguage
import com.example.mymovielibrary.domain.model.events.SettingsEvent.CollectionsToStatistics
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
    private val authRepo: AuthRepo,
    private val localStoreWriter: LocalStoreWriter,
    private val localStoreReader: LocalStoreReader
): BaseViewModel() {

    private val _settingsState = MutableStateFlow(getCurrentSettings())
    val settingsState = _settingsState.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is ChangeCountry -> {}
            is ChangeResponseLanguage -> saveNewLanguage(event.language)
            is CollectionsToStatistics -> {}
            Logout -> logout()
        }
    }

    private fun getCurrentSettings(): SettingsState {
        val currentIso = localStoreReader.iso639
        val currentName = getNameByIso(currentIso)
        return SettingsState(LanguageDetails(name = currentName, iso = currentIso))
    }

    private fun saveNewLanguage(language: String) {
        val iso639 = getIsoByName(language)
        localStoreWriter.saveNewResponseLanguage(iso639)
        //в стейт сейвить нет смысла
    }

    private fun logout() {
        //todo добавить уведомление о успешном логауте
        //todo уведомить экран профиля о логауте. Пока видимого выхода юзера нет, пока viewModel не пересоздастся
        viewModelScope.launch {
//            val isSuccess = authRepo.logout().getOrThrow()
            val isSuccess = request { authRepo.logout() }
            localStoreWriter.clearInfo()
        }
    }

    private fun getNameByIso(iso: String): String {
        return getLanguages().find { it.iso == iso }!!.name
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