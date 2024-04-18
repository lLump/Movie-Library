package com.example.mymovielibrary.data.navigation

import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.navigation.model.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetStartScreen  @AssistedInject constructor(
    private val userCreds: UserCredentials,
    @Assisted private val onEvent: (AuthEvent) -> Unit
) {
    operator fun invoke(): String {
        var screenRoute: NavigationRoute = Screen.HOME
        userCreds.getUserIfSaved { (isSaved, user) ->
            screenRoute = if (isSaved) {
                onEvent(AuthEvent.LoginSession(user, false))
                Screen.HOME
            } else Screen.AUTH
        }
        return screenRoute()
    }
}

interface GetStartScreenFactory {
    fun create(onEvent: (AuthEvent) -> Unit): GetStartScreen

    @AssistedFactory
    interface Factory {
        fun create(@Assisted onEvent: (AuthEvent) -> Unit): GetStartScreen
    }
}