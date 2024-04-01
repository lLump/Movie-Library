package com.example.mymovielibrary.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.mymovielibrary.data.navigation.GetStartScreen
import com.example.mymovielibrary.data.navigation.GetStartScreenFactory
import com.example.mymovielibrary.presentation.auth.navigation.addAuthScreen
import com.example.mymovielibrary.presentation.viewmodel.AppViewModel
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.model.uiText.UiText
import com.example.mymovielibrary.presentation.model.LoadingState
import javax.inject.Inject

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<AppViewModel>()
//    AttachDisposableEffectTo(viewModel, LocalLifecycleOwner.current)
//    LaunchedEffect(Unit) {
//        viewModel.events.collect { event ->
//            when (event) {
//                is UiEvent.Error -> {
////                    showToast(event.error, navController.context)
//                }
//
//                is UiEvent.Loading -> {
//                    when (event.loading) {
//                        LoadingState.EMPTY -> { }
//                        LoadingState.SUCCESS -> { }
//                        LoadingState.LOADING -> { }
//                        LoadingState.FAILURE -> { }
//                    }
//                }
//            }
//        }
//    }

    NavHost(navController = navController, startDestination = viewModel.getStartScreen()) {
        addAuthScreen(
            navController = navController,
            authEvent = viewModel::onEvent,
            state = viewModel.events
        )
        navigation(startDestination = Screen.HOME(), route = Navigation.MAIN()) {
            composable(Screen.HOME()) {
                HomeScreen()
            }
        }
    }
}

@Composable
private fun AttachDisposableEffectTo(viewModel: AppViewModel, lifecycleOwner: LifecycleOwner) {

//    DisposableEffect(key1 = viewModel) {
//        val observer = LifecycleEventObserver { source, event ->
//            when (event) {
//                Lifecycle.Event.ON_CREATE -> viewModel.onEvent(CustomEvent.OnStartUp)
//                else -> { }
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }
}

@Composable
fun HomeScreen() {
    Text(text = "HOME", modifier = Modifier.fillMaxWidth())
}
