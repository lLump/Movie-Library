package com.example.mymovielibrary.presentation.ui.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.presentation.viewmodel.states.LoadingState
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.navigation.model.Screen
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.addAuthScreen(
    navController: NavController,
    authEvent: (AuthEvent) -> Unit,
    state: Flow<UiEvent>
) {
//    composable(Screen.AUTH()) {
//        val loadingState by state.collectAsState(UiEvent.Loading(LoadingState.EMPTY))
//        AuthScreen(
//            event = loadingState,
//            onEvent = authEvent,
//            navigateToHome = {
////                navController.popBackStack() //clear authScreen from backStack
////                navController.navigate(Screen.HOME())
//                redirectToApproveToken(navController.context)
//            },
//            registration = {
//                redirectToRegistration(
//                    context = navController.context
//                )
//            }
//        )
//    }
}