package com.example.mymovielibrary.data.auth.helper

import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.LocalUserInfo
import com.example.mymovielibrary.presentation.viewmodel.states.LoadingState
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.presentation.model.UiEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthHelperImpl(
    private val scope: CoroutineScope,
    private val authRepo: AuthRepository,
    private val userInfo: LocalUserInfo,
    private val getAccountId: GetAccountId //TODO удалить
) : AuthHelper, UiEventListener {
    private lateinit var sendUiEvent: suspend (UiEvent) -> Unit
    override fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit) {
        sendUiEvent = collectUiEvent
    }

    private var isUserLoggedIn: Boolean //FIXME (костыль)

    init {
        isUserLoggedIn = isUserInfoExist()
        if (!isUserLoggedIn) {
//            guestLogin()
        }
    }

    private fun isUserInfoExist(): Boolean {
        var result = false
        userInfo.getInfoIfExist { isSaved, accountId, sessionId ->
            if (isSaved) {
                TmdbData.run {
                    this.accountIdV4 = accountId
                    this.sessionId = sessionId
                    //TODO language ISO
                }
                result = true
            }
        }
        return result
    }

    private fun guestLogin() {
        scope.launch(Dispatchers.IO) {
            TmdbData.sessionId = //getting & saving guestSessionId
                request { authRepo.getGuestSessionId() } ?: "noSessionId"
        }
    }

    override suspend fun getRequestToken() = request { authRepo.createRequestTokenV4() } ?: "noToken"

    override fun saveTmdbInfo(requestToken: String) {
        scope.launch(Dispatchers.IO) {
            val (accountId, token) = request { authRepo.createAccessTokenV4(requestToken) }
                ?: Pair("", "noToken")
            val sessionId = request { authRepo.getSessionIdV4(token) } ?: "noSessionId"
            TmdbData.run {
                this.accountIdV4 = accountId
                this.accessToken = token
                this.sessionId = sessionId
            }
            //TODO accountIdV3
            userInfo.saveUserInfo(accountId, sessionId) //local save
        }
    }

    private suspend fun <D> request(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                sendUiEvent(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}