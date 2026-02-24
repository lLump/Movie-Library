package com.example.mymovielibrary.presentation.ui.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.local.SettingsWriter
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.model.events.SettingsEvent
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeCountry
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeResponseLanguage
import com.example.mymovielibrary.domain.model.events.SettingsEvent.CollectionsToStatistics
import com.example.mymovielibrary.domain.model.events.SettingsEvent.Logout
import com.example.mymovielibrary.presentation.ui.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val localSettingsWriter: SettingsWriter,
    private val localSettingsReader: SettingsReader
): BaseViewModel() {

    private val _settingsState = MutableStateFlow(getCurrentSettings())
    val settingsState = _settingsState.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is ChangeCountry -> {}
            is ChangeResponseLanguage -> saveNewLanguage(event.language)
            is CollectionsToStatistics -> saveUserCollectionsInfo(event.userCollections)
            Logout -> logout()
        }
    }

    private fun saveUserCollectionsInfo(userCollections: List<UserCollectionInfo>) {
        localSettingsWriter.saveUserCollectionsForStats(userCollections)
    }

    private fun getCurrentSettings(): SettingsState {
        return SettingsState(localSettingsReader.language)
    }

    private fun saveNewLanguage(language: String) {
        localSettingsWriter.saveApiResponseLanguage(language)
        //в стейт сейвить нет смысла
    }

    private fun logout() {
        //todo добавить уведомление о успешном логауте
        //todo уведомить экран профиля о логауте. Пока видимого выхода юзера нет, пока viewModel не пересоздастся
        viewModelScope.launch {
//            val isSuccess = authRepo.logout().getOrThrow()
            val isSuccess = request { authRepo.logout() }
            localSettingsWriter.clearInfo()
        }
    }
}