package com.example.mymovielibrary.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymovielibrary.presentation.account.ui.ProfileScreen
import com.example.mymovielibrary.presentation.auth.navigation.addAuthScreen
import com.example.mymovielibrary.presentation.navigation.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.viewmodel.AppViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<AppViewModel>()

    val visibilityBottomBar = remember { mutableStateOf(false) }
    navController.setupDestinationListener(visibilityBottomBar)

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

    Scaffold(
        bottomBar = {
            if (visibilityBottomBar.value) {
                MyBottomBar(navController = navController)
            }
        },
        content = { padding ->
            padding
            NavHost(navController = navController, startDestination = viewModel.getStartScreen()) {
                addAuthScreen(
                    navController = navController,
                    authEvent = viewModel::onEvent,
                    state = viewModel.events
                )
                composable(Screen.LISTS()) {
                    MainScreen()
                }
                composable(Screen.PROFILE()) {
                    ProfileScreen()
                }
                composable(Screen.HOME()) {
                    HomeScreen()
                }
            }
        }
    )
}

private fun NavHostController.setupDestinationListener(visibilityBottomBar: MutableState<Boolean>) {
    this.addOnDestinationChangedListener { _, _, _ ->
        val currentRoute = this.currentBackStackEntry?.destination?.route
        visibilityBottomBar.value = when (currentRoute) {
            Screen.AUTH() -> false
            else -> true
        }
    }
}

//    NavHost(navController = navController, startDestination = viewModel.getStartScreen()) {
//        addAuthScreen(
//            navController = navController,
//            authEvent = viewModel::onEvent,
//            state = viewModel.events
//        )
////        navigation(startDestination = Screen.HOME(), route = Navigation.MAIN()) {
//        composable(Navigation.MAIN()) { //Screen.home() when navigation was
//            Scaffold(
//                bottomBar = { MyBottomBar(navController) },
//                content = {
//                    ProfileScreen()
//                    it
//                }
//            )
//        }
//        composable(Screen.PROFILE()) {
//            ProfileScreen()
//        }
//        composable(Screen.HOME()) {
//            HomeScreen()
//        }
////        }
//    }
//}

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

@Composable
fun MainScreen() {
    Text(text = "Main", modifier = Modifier.fillMaxWidth())
}