package com.example.mymovielibrary.data.auth.helper

import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.domain.auth.model.UserInfo
import com.example.mymovielibrary.presentation.viewmodel.states.LoadingState
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.navigation.model.Screen
import com.example.mymovielibrary.domain.model.Result
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.presentation.model.UiEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthHelperImpl(
    private val scope: CoroutineScope,
    private val authRepo: AuthRepository,
    private val userCreds: UserCredentials,
    private val getAccountId: GetAccountId
) : AuthHelper, UiEventListener {
    private lateinit var sendUiEvent: suspend (UiEvent) -> Unit
    override fun setCollector(collectUiEvent: suspend (UiEvent) -> Unit) {
        sendUiEvent = collectUiEvent
    }

    init {
        scope.launch(Dispatchers.IO) {
            initToken() //TODO change to init in login functions
        }
    }

    override fun getStartScreen(): String {
        var screenRoute: NavigationRoute = Screen.HOME
        userCreds.getUserIfSaved { (isSaved, user) ->
            screenRoute = if (isSaved) {
                performLogin(user, false)
                Screen.HOME
            } else Screen.AUTH
        }
        return screenRoute()
    }

    override fun guestLogin() {
        scope.launch(Dispatchers.IO) {
            sendUiEvent(UiEvent.Loading(LoadingState.LOADING))
            TmdbData.sessionId = //getting & saving guestSessionId
                executeApiCall { authRepo.getGuestSessionId() } ?: "noSessionId"
            if (TmdbData.sessionId == "noSessionId") {
                sendUiEvent(UiEvent.Loading(LoadingState.FAILURE))
            } else {
                sendUiEvent(UiEvent.Loading(LoadingState.SUCCESS))
            }
        }
    }

    override fun performLogin(user: UserInfo, needToSave: Boolean) {
        scope.launch(Dispatchers.IO) {
            sendUiEvent(UiEvent.Loading(LoadingState.LOADING))
            if (isTokenValidated(user)) {
                sendUiEvent(UiEvent.Loading(LoadingState.SUCCESS))
                if (needToSave) { //saving user into prefs
                    userCreds.saveUserCredentials(user)
                }
                TmdbData.sessionId = //getting & saving sessionId
                    executeApiCall { authRepo.getSessionId(TmdbData.requestToken) } ?: "noSessionId"
                TmdbData.accountId = //getting & saving accountId
                    executeApiCall { getAccountId(TmdbData.sessionId) } ?: 0
            } else sendUiEvent(UiEvent.Loading(LoadingState.FAILURE))
        }
    }

    private suspend fun isTokenValidated(user: UserInfo): Boolean {
        getTokenIfNotExist()
        return executeApiCall {
            authRepo.validateToken( //validation
                token = TmdbData.requestToken,
                username = user.username,
                password = user.password
            )
        } ?: false
    }

    private suspend fun getTokenIfNotExist() { //Костыль to fix 1 bug, when user opened app without internet
        if (TmdbData.requestToken == "noToken") {
            initToken()
        }
    }

    private suspend fun initToken() {
        TmdbData.requestToken = executeApiCall { authRepo.getToken() } ?: "noToken"
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