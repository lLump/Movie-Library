package com.example.mymovielibrary.data.lists.helper

import com.example.mymovielibrary.data.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.UiEventListener
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import kotlinx.coroutines.CoroutineScope

class ListHelperImpl(
    private val listRepo: ListRepository
): ListHelper, BaseHelper() {

    override suspend fun getUserCollections(): List<UserCollection> {
        return executeApiCall { listRepo.getUserCollections() } ?: return emptyList()
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