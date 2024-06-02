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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.presentation.ui.profile.screen.ProfileScreen
import com.example.mymovielibrary.presentation.navigation.bar.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.*
import com.example.mymovielibrary.presentation.ui.lists.screen.ListsScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.UserCollectionsScreen
import com.example.mymovielibrary.presentation.ui.lists.viewModel.ListViewModel
import com.example.mymovielibrary.presentation.ui.profile.viewModel.ProfileViewModel
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(isTokenApproved: Boolean) {
    val navController = rememberNavController()

    val visibilityBottomBar = remember { mutableStateOf(false) }
    navController.setupDestinationListener(visibilityBottomBar)

    Scaffold(
        bottomBar = { if(visibilityBottomBar.value) MyBottomBar(navController = navController) },
//        topBar = {  },
        content = { padding ->
            NavHost(navController = navController, startDestination = Home) {
                //NavBar
                composable<Home> {
                    HomeScreen()
                }
                composable<Lists>{
//                    val listState by viewModel.listState.collectAsState()
                    val viewModel: ListViewModel = hiltViewModel()
                    ListsScreen(
                        viewModel = viewModel,
                        padding = padding,
//                        onEvent = viewModel::onEvent,
//                        state = listState,
                        toScreen = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }
                composable<Profile> {
                    val viewModel: ProfileViewModel = hiltViewModel()
                    ProfileScreen(
                        viewModel = viewModel,
                        padding = padding,
                        redirectToUrl = { url ->
                            redirectToUrl(url, navController.context)
                        },
                        toScreen = { screen ->
                            navController.navigate(screen)
                        },
                        isFromApproving = isTokenApproved
                    )
                }
                //NavBar

                //Screens in Lists
                composable<Collections> {
                    HomeScreen()
                }
                composable<CollectionDetails> {
                    val viewModel: ListViewModel = hiltViewModel()
                    val args = it.toRoute<CollectionDetails>()
                    UserCollectionsScreen(
                        viewModel = viewModel,
                        collectionId = args.collectionId
                    )
                }
                composable<MediaDetails> {
                    HomeScreen()
                }
                //Screens in Lists
            }
        }
    )
}

fun redirectToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

private fun NavHostController.setupDestinationListener(visibilityBottomBar: MutableState<Boolean>) {
    this.addOnDestinationChangedListener { _, _, _ ->
        val currentRoute = this.currentBackStackEntry?.destination?.route
        val formattedRoute = currentRoute?.substringAfterLast(".")?.substringBefore("/")
        val t =  CollectionDetails.toString().contains(formattedRoute.toString())
        val s = formattedRoute?.let { (CollectionDetails.toString()).contains(it) }
        val g = 1
        visibilityBottomBar.value = when {
            CollectionDetails.toString().contains(formattedRoute.toString()) -> false
            formattedRoute?.let { (Collections.toString()).contains(it) } == true -> false
            formattedRoute?.let { (CollectionDetails.toString()).contains(it) } == true -> false
            formattedRoute?.let { MediaDetails.toString().contains(it) } == true -> false
            else -> true
        }
    }
}

@Composable
private fun ProfileViewModel.AttachDisposableEffect(lifecycleOwner: LifecycleOwner) {

    DisposableEffect(key1 = this) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> this@AttachDisposableEffect.onEvent(ProfileEvent.LoadUserScreen)
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