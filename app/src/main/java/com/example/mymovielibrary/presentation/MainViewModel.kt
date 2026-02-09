package com.example.mymovielibrary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.model.handlers.getOrThrow
import com.example.mymovielibrary.domain.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val localStoreReader: LocalStoreReader,
    private val localStoreWriter: LocalStoreWriter
): ViewModel() {
    val authState = authRepo.authState.stateIn(viewModelScope, SharingStarted.Eagerly, AuthState.Loading)

    fun onTokenApprove() {
        viewModelScope.launch(Dispatchers.IO) {
            val (accId, accessToken) = authRepo.createAccessTokenV4(localStoreReader.requestToken!!).getOrThrow()
            val sessionId = authRepo.getSessionIdV4(accessToken).getOrThrow()
            localStoreWriter.saveUserInfo(
                accountIdV4 = accId,
                sessionId = sessionId,
                accessToken = accessToken
            )
            authRepo.authorizeUser()
        }
    }
}