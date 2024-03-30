package com.example.mymovielibrary.auth.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mymovielibrary.auth.presentation.ui.AuthScreen
import com.example.mymovielibrary.auth.domain.model.AuthEvent
import com.example.mymovielibrary.auth.presentation.model.LoadingState
import com.example.mymovielibrary.core.presentation.navigation.Navigation
import com.example.mymovielibrary.core.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.addAuthScreen(
    navController: NavController,
    authEvent: (AuthEvent) -> Unit,
    state: Flow<LoadingState>
) {
    composable(Screen.AUTH()) {
        val loadingState by state.collectAsState(LoadingState.EMPTY)
        AuthScreen(
            loadingState = loadingState,
            onEvent = authEvent,
            navigateToHome = {
                navController.navigate(Navigation.MAIN()) {
                    popUpTo(Navigation.MAIN()) {
                        inclusive = true
                    }
                }
            },
            registration = {
                redirectToRegistration(
                    context = navController.context
                )
            }
        )
    }
}

private fun redirectToRegistration(context: Context) {
    val url = "https://www.themoviedb.org/signup"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}