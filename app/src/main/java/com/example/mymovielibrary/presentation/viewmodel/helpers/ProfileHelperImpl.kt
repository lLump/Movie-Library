package com.example.mymovielibrary.presentation.viewmodel.helpers

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.helper.ProfileHelper
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProfileHelperImpl(
    private val scope: CoroutineScope,
    private val accConfig: AccountRepository
) : ProfileHelper, UiEventListener {
    private lateinit var sendUiEvent: suspend (UiEvent) -> Unit

    override fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit) {
        sendUiEvent = collectUiEvent
    }

    override fun loadLanguages(callback: (List<LanguageDetails>) -> Unit) {
        scope.launch {
            val list = executeApiCall { accConfig.getLanguages() } ?: arrayListOf()
            callback(list)
        }
    }

    override fun loadAvatar(): ImageVector {
        TODO("Not yet implemented")
    }

    private suspend fun <D> executeApiCall(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                sendUiEvent(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}