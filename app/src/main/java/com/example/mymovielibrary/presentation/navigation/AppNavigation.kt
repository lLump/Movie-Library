package com.example.mymovielibrary.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.presentation.model.UiEvent
import com.example.mymovielibrary.presentation.ui.profile.ProfileScreen
import com.example.mymovielibrary.presentation.ui.auth.addAuthScreen
import com.example.mymovielibrary.presentation.navigation.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.navigation.model.Screen
import com.example.mymovielibrary.presentation.ui.lists.ListsScreen
import com.example.mymovielibrary.presentation.viewmodel.AppViewModel
import com.example.mymovielibrary.presentation.viewmodel.states.LoadingState

@Composable
fun AppNavigation(isTokenApproved: Boolean) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<AppViewModel>()

    val visibilityBottomBar = remember { mutableStateOf(false) }
    navController.setupDestinationListener(visibilityBottomBar)

    if(isTokenApproved) {
        viewModel.onEvent(AuthEvent.ApproveToken)
    }
    DisposableEffect(viewModel.token) {
        val observer = Observer<String> { requestToken ->
            redirectToApproving(
                token = requestToken,
                context = navController.context
            )
        }
        viewModel.token.observeForever(observer)
        onDispose {
            viewModel.token.removeObserver(observer)
        }
    }

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
            NavHost(navController = navController, startDestination = Screen.HOME()) {
                addAuthScreen(
                    navController = navController,
                    authEvent = viewModel::onEvent,
                    state = viewModel.events
                )
                composable(Screen.HOME()) {
                    HomeScreen(viewModel)
                }
                composable(Screen.LISTS()) {
                    val listState by viewModel.listState.collectAsState()
                    ListsScreen(
                        onEvent = viewModel::onEvent,
                        state = listState
                    )
                }
                composable(Screen.PROFILE()) {
                    val profileState by viewModel.profileState.collectAsState()
                    val uiEvents by viewModel.events.collectAsState(UiEvent.Loading(LoadingState.EMPTY))
//                        viewModel.onEvent(ProfileEvent.LoadProfile) //TODO Test it
                    ProfileScreen(
                        profile = profileState,
                        onEvent = viewModel::onEvent,
                        padding = padding
//                        uiEvent = uiEvents,
//                        registration = {
//                            redirectToRegistration(navController.context)
//                        },
//                        approveToken = {
//                            redirectToApproving(
//                                token = viewModel.token.value,
//                                context = navController.context
//                            )
//                        }
                    )
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

private fun redirectToApproving(token: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/auth/access?request_token=$token"))
    context.startActivity(intent)
}
private fun redirectToRegistration(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/signup"))
    context.startActivity(intent)
}

@Composable
private fun AttachDisposableEffectTo(viewModel: AppViewModel, lifecycleOwner: LifecycleOwner) {

    DisposableEffect(key1 = viewModel) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
//                Lifecycle.Event.ON_CREATE -> viewModel.onEvent()
                else -> { }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun HomeScreen(viewModel: AppViewModel) {
    Text(text = "HOME", modifier = Modifier.fillMaxWidth())
}