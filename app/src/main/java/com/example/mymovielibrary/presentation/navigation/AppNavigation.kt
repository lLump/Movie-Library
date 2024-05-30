package com.example.mymovielibrary.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.presentation.ui.profile.screen.ProfileScreen
import com.example.mymovielibrary.presentation.navigation.bar.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.navigation.model.Screen
import com.example.mymovielibrary.presentation.ui.lists.ListsScreen
import com.example.mymovielibrary.presentation.ui.profile.viewModel.ProfileViewModel

@Composable
fun AppNavigation(isTokenApproved: Boolean) {
    val navController = rememberNavController()

//    val visibilityBottomBar = remember { mutableStateOf(false) }
//    navController.setupDestinationListener(visibilityBottomBar)

    Scaffold(
        bottomBar = { MyBottomBar(navController = navController) },
        topBar = {  },
        content = { padding ->
            NavHost(navController = navController, startDestination = Screen.HOME()) {
                composable(Screen.HOME()) {
                    HomeScreen()
                }
                composable(Screen.LISTS()) {
//                    val listState by viewModel.listState.collectAsState()
                    ListsScreen(
//                        onEvent = viewModel::onEvent,
//                        state = listState
                    )
                }
                composable(Screen.PROFILE()) {
                    ProfileScreen(
                        padding = padding,
                        redirectToUrl = { url ->
                            redirectToUrl(url, navController.context)
                        },
                        toScreen = { screen ->
                                   navController.navigate(screen())
                        },
                        isFromApproving = isTokenApproved
                    )
                }
            }
        }
    )
}

fun redirectToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

//private fun NavHostController.setupDestinationListener(visibilityBottomBar: MutableState<Boolean>) {
//    this.addOnDestinationChangedListener { _, _, _ ->
//        val currentRoute = this.currentBackStackEntry?.destination?.route
//        visibilityBottomBar.value = when (currentRoute) {
//            Screen.AUTH() -> false
//            else -> true
//        }
//    }
//}

@Composable
private fun ProfileViewModel.AttachDisposableEffect(lifecycleOwner: LifecycleOwner) {

    DisposableEffect(key1 = this) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> this@AttachDisposableEffect.onEvent(ProfileEvent.LoadUserDetails)
//                Lifecycle.Event.ON_START -> onEvent(ProfileEvent.LoadUserDetails)
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
fun HomeScreen() {
    Text(text = "HOME", modifier = Modifier.fillMaxWidth())
}