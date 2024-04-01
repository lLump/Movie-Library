package com.example.mymovielibrary.data.auth.repository

import com.example.mymovielibrary.data.TempTmdbData
import com.example.mymovielibrary.domain.account.repository.AccountIdGetter
import com.example.mymovielibrary.domain.auth.repository.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.domain.auth.model.UserInfo
import com.example.mymovielibrary.presentation.model.LoadingState
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.uiText.asErrorUiText
import com.example.mymovielibrary.presentation.navigation.Navigation
import com.example.mymovielibrary.presentation.navigation.NavigationRoute
import com.example.mymovielibrary.presentation.navigation.Screen
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
    private val idGetter: AccountIdGetter
) : AuthHelper {

    private lateinit var eventListener: UiEventListener

    init {
        scope.launch(Dispatchers.IO) {
            initToken()
        }
    }

    override fun setEventListener(listener: UiEventListener) {
        eventListener = listener
    }

//    override fun setCollector(collectUiEvent: (UiEvent) -> Unit) {
//        sendUiEvent = collectUiEvent
//    }

    override fun getStartScreen(): String {
        var screenRoute: NavigationRoute = Navigation.MAIN
        userCreds.getUserIfSaved { (isSaved, user) ->
            screenRoute = if (isSaved) {
                performLogin(user, false)
                Navigation.MAIN
            } else Screen.AUTH
        }
        return screenRoute()
    }

    override fun guestLogin() {
        scope.launch(Dispatchers.IO) {
            eventListener.collectUiEvent(UiEvent.Loading(LoadingState.LOADING))
            TempTmdbData.sessionId =
                executeApiCall { authRepo.createGuestSession() } ?: "noSessionId"
            if (TempTmdbData.sessionId == "noSessionId") {
                eventListener.collectUiEvent(UiEvent.Loading(LoadingState.FAILURE))
            } else {
                eventListener.collectUiEvent(UiEvent.Loading(LoadingState.SUCCESS))
            }
        }
    }

    override fun performLogin(user: UserInfo, needToSave: Boolean) {
        scope.launch(Dispatchers.IO) {
            eventListener.collectUiEvent(UiEvent.Loading(LoadingState.LOADING))
            if (isTokenValidated(user)) {
                eventListener.collectUiEvent(UiEvent.Loading(LoadingState.SUCCESS))
                if (needToSave) { //saving user into prefs
                    userCreds.saveUserCredentials(user)
                }
                TempTmdbData.sessionId = //getting sessionId
                    executeApiCall { authRepo.createSession(TempTmdbData.requestToken) }
                        ?: "noSessionId"
                TempTmdbData.accountId = //getting accountId
                    executeApiCall { idGetter.getAccountId(TempTmdbData.sessionId) } ?: 0
            } else eventListener.collectUiEvent(UiEvent.Loading(LoadingState.FAILURE))
        }
    }

    private suspend fun isTokenValidated(user: UserInfo): Boolean {
        getTokenIfNotExist()
        return executeApiCall {
            authRepo.validateToken( //validation
                token = TempTmdbData.requestToken,
                username = user.username,
                password = user.password
            )
        } ?: false
    }

    private suspend fun getTokenIfNotExist() { //Костыль to fix 1 bug, when user opened app without internet
        if (TempTmdbData.requestToken == "noToken") {
            initToken()
        }
    }

    private suspend fun initToken() {
        TempTmdbData.requestToken = executeApiCall { authRepo.getToken() } ?: "noToken"
    }

    private suspend fun <D> executeApiCall(request: suspend () -> Result<D, DataError>): D? {
        return when (val result = request.invoke()) {
            is Result.Success -> result.data
            is Result.Error -> {
                eventListener.collectUiEvent(UiEvent.Error(result.asErrorUiText()))
                null
            }
        }
    }
}