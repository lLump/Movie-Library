package com.example.mymovielibrary.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.presentation.ui.profile.ProfileScreen
import com.example.mymovielibrary.presentation.ui.auth.addAuthScreen
import com.example.mymovielibrary.presentation.navigation.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.navigation.model.Screen
import com.example.mymovielibrary.presentation.ui.lists.ListsScreen
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
            padding //Fixme
            NavHost(navController = navController, startDestination = viewModel.getStartScreen()) {
                addAuthScreen(
                    navController = navController,
                    authEvent = viewModel::onEvent,
                    state = viewModel.events
                )
                composable(Screen.HOME()) {
                    HomeScreen()
                }
                composable(Screen.LISTS()) {
                    val listState by viewModel.listState.collectAsState()
                    ListsScreen(
                        onEvent = viewModel::onEvent,
                        state = listState
                    )
                }
                composable(Screen.PROFILE()) {
                    if (TmdbData.accountId != 0) {
                        val profileState by viewModel.profileState.collectAsState()
//                        viewModel.onEvent(ProfileEvent.LoadProfile) //TODO Test it
                        ProfileScreen(
                            state = profileState,
                            onEvent = viewModel::onEvent
                        )
                    } else {
                        //TODO отобразить экран для гостя
                    }
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