package com.example.mymovielibrary.presentation.ui.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.domain.model.events.SettingsEvent
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeCountry
import com.example.mymovielibrary.domain.model.events.SettingsEvent.ChangeResponseLanguage
import com.example.mymovielibrary.domain.model.events.SettingsEvent.CollectionsToStatistics
import com.example.mymovielibrary.domain.model.events.SettingsEvent.Logout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

): BaseViewModel() {

//    private val _settingsState by lazy { MutableStateFlow<SettingsState> }
//    val settingsState = _settingsState.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is ChangeCountry -> TODO()
            is ChangeResponseLanguage -> saveNewLanguage(event.language)
            is CollectionsToStatistics -> TODO()
            Logout -> TODO()
        }
    }

    private fun saveNewLanguage(language: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val iso639 = language.substringBefore("-")
            val iso3166 = language.substringAfter("-")
            Store.tmdbData.iso639 = iso639
            Store.tmdbData.iso3166 = iso3166
        }
    }

    private fun changeCountry(country: String) {

    }

    private fun logout() {

    }
}