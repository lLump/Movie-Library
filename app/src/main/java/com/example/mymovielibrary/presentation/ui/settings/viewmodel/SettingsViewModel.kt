package com.example.mymovielibrary.presentation.ui.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.model.UserCollectionInfo
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.local.SettingsWriter
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.model.events.SettingsEvent
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeCountry
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeResponseLanguage
import com.example.mymovielibrary.domain.model.events.SettingsEvent.AddCollectionToStatistics
import com.example.mymovielibrary.domain.model.events.SettingsEvent.RemoveCollectionFromStatistics
import com.example.mymovielibrary.domain.model.events.SettingsEvent.Logout
import com.example.mymovielibrary.presentation.ui.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val localSettingsWriter: SettingsWriter,
    private val localPrefs: LocalStoreWriter,
    private val localSettingsReader: SettingsReader
): BaseViewModel() {

    private val _settingsState = MutableStateFlow(getCurrentSettings())
    val settingsState = _settingsState.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is ChangeCountry -> {}
            is ChangeResponseLanguage -> saveNewLanguage(event.language)
            is AddCollectionToStatistics -> addCollectionToStats(event.collection)
            is RemoveCollectionFromStatistics -> removeCollectionFromState(event.collection)
            Logout -> logout()
        }
    }

    private fun addCollectionToStats(collection: UserCollectionInfo) {
        localSettingsWriter.saveUserCollectionForStats(collection) //todo сейвить отсюда списком
        _settingsState.update { state ->
            val newSelected = state.selectedCollections.toMutableSet()
            newSelected.add(collection.id)
            state.copy(
                selectedCollections = newSelected.toSet()
            )
        }
    }

    private fun removeCollectionFromState(collection: UserCollectionInfo) {
        localSettingsWriter.removeUserCollectionFromStats(collection)
        _settingsState.update { state ->
            val newSelected = state.selectedCollections.toMutableSet()
            newSelected.remove(collection.id)
            state.copy(
                selectedCollections = newSelected.toSet()
            )
        }
    }

    private fun getCurrentSettings(): SettingsState {
        return SettingsState(
            language = localSettingsReader.language,
            userCollections = localSettingsReader.userCollections,
            selectedCollections = localSettingsReader.userCollectionsForStats.toSet()
        )
    }

    private fun saveNewLanguage(language: String) {
        localSettingsWriter.saveApiResponseLanguage(language)
        //в стейт сейвить нет смысла
    }

    private fun logout() {
        //todo добавить уведомление о успешном логауте
        //todo уведомить экран профиля о логауте
        viewModelScope.launch {
//            val isSuccess = authRepo.logout().getOrThrow()
            val isSuccess = request { authRepo.logout() }
            localPrefs.clearInfo()
        }
    }
}