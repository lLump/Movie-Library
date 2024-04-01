package com.example.mymovielibrary.data.navigation

import com.example.mymovielibrary.domain.auth.model.AuthEvent
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.presentation.navigation.Navigation
import com.example.mymovielibrary.presentation.navigation.NavigationRoute
import com.example.mymovielibrary.presentation.navigation.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class GetStartScreen  @AssistedInject constructor(
    private val userCreds: UserCredentials,
    @Assisted private val onEvent: (AuthEvent) -> Unit
) {
    operator fun invoke(): String {
        var screenRoute: NavigationRoute = Navigation.MAIN
        userCreds.getUserIfSaved { (isSaved, user) ->
            screenRoute = if (isSaved) {
                onEvent(AuthEvent.LoginSession(user, false))
                Navigation.MAIN
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