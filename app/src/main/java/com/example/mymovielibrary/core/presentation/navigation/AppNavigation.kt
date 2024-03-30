package com.example.mymovielibrary.core.presentation.navigation

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
import com.example.mymovielibrary.auth.domain.repository.UserStore
import com.example.mymovielibrary.auth.presentation.navigation.addAuthScreen
import com.example.mymovielibrary.core.presentation.viewmodel.AppViewModel
import com.example.mymovielibrary.core.presentation.ui.model.UiEvent
import com.example.mymovielibrary.core.presentation.ui.uiText.UiText
import javax.inject.Inject

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<AppViewModel>()

//    AttachDisposableEffectTo(viewModel, LocalLifecycleOwner.current)
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.Error -> {
                    showToast(event.error, navController.context)
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = viewModel.getStartScreen()) {
        addAuthScreen(
            navController = navController,
            authEvent = viewModel::onEvent,
            state = viewModel.loadingState
        )
        navigation(startDestination = Screen.HOME(), route = Navigation.MAIN()) {
            composable(Screen.HOME()) {
                HomeScreen()
            }
        }
    }
}

private fun showToast(message: UiText, context: Context) {
    Toast.makeText(context, message.asString(context), Toast.LENGTH_LONG).show()
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
