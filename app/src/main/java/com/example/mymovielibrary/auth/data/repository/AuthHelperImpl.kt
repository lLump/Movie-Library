package com.example.mymovielibrary.auth.data.repository

import com.example.mymovielibrary.auth.domain.repository.AuthHelper
import com.example.mymovielibrary.auth.domain.repository.AuthRepository
import com.example.mymovielibrary.auth.domain.repository.UserStore
import com.example.mymovielibrary.auth.domain.model.UserInfo
import com.example.mymovielibrary.auth.presentation.model.LoadingState
import com.example.mymovielibrary.core.presentation.ui.model.UiEvent
import com.example.mymovielibrary.core.presentation.ui.uiText.asErrorUiText
import com.example.mymovielibrary.core.presentation.navigation.Navigation
import com.example.mymovielibrary.core.presentation.navigation.NavigationRoute
import com.example.mymovielibrary.core.presentation.navigation.Screen
import com.example.mymovielibrary.core.domain.model.Result
import com.example.mymovielibrary.core.domain.model.DataError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class AuthHelperImpl(
    private val scope: CoroutineScope,
    private val authRepo: AuthRepository,
    private val userStore: UserStore,
    private val loadingState: MutableStateFlow<LoadingState>,
    private val sendUiEvent: (UiEvent) -> Unit,
): AuthHelper {

    init {
        scope.launch(Dispatchers.IO) {
            initToken()
        }
    }

    override fun getStartScreen(): String {
        var screenRoute: NavigationRoute = Navigation.MAIN
        userStore.getUserIfSaved { (isSaved, user) ->
            screenRoute = if (isSaved) {
                performLogin(user, false)
                Navigation.MAIN
            } else Screen.AUTH
        }
        return screenRoute()
    }

    override fun guestLogin() {
        scope.launch(Dispatchers.IO) {
            loadingState.emit(LoadingState.LOADING)
            userStore.sessionId = executeApiCall { authRepo.createGuestSession() } ?: "noSessionId"
            if (userStore.sessionId == "noSessionId") {
                loadingState.emit(LoadingState.FAILURE)
            } else loadingState.emit(LoadingState.SUCCESS)
        }
    }

    override fun login(login: String, password: String, needToSave: Boolean) {
        performLogin(UserInfo(login, password), needToSave)
    }

    private fun performLogin(user: UserInfo, needToSave: Boolean) {
        scope.launch(Dispatchers.IO) {
            loadingState.emit(LoadingState.LOADING)
            if (isTokenValidated(user)) {
                loadingState.emit(LoadingState.SUCCESS)
                if (needToSave) { //saving user into prefs
                    userStore.saveUserCredentials(user)
                }
                userStore.sessionId = //getting sessionId
                    executeApiCall { authRepo.createSession(userStore.requestToken) }
                         ?: "noSessionId"
            } else loadingState.emit(LoadingState.FAILURE)
        }
    }

    private suspend fun isTokenValidated(user: UserInfo): Boolean {
        getTokenIfNotExist()
        return executeApiCall {
            authRepo.validateToken( //validation
                token = userStore.requestToken,
                username = user.username,
                password = user.password
            )
        } ?: false
    }

    private suspend fun getTokenIfNotExist() {
        if (userStore.requestToken == "noToken") {
            initToken()
        }
    }

    private suspend fun initToken() {
        userStore.requestToken = executeApiCall { authRepo.getToken() } ?: "noToken"
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