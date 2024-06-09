package com.example.mymovielibrary.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.presentation.ui.profile.screen.ProfileScreen
import com.example.mymovielibrary.presentation.navigation.bar.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.*
import com.example.mymovielibrary.presentation.ui.lists.screen.ListsScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.ChosenCollectionScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.FavoritesScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.RatedScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.WatchlistScreen
import com.example.mymovielibrary.presentation.ui.lists.viewModel.ListViewModel
import com.example.mymovielibrary.presentation.ui.profile.viewModel.ProfileViewModel

@Composable
fun AppNavigation(isTokenApproved: Boolean) {
    val navController = rememberNavController()

    val visibilityBottomBar = remember { mutableStateOf(true) }
    navController.setupDestinationListener(visibilityBottomBar)

    Scaffold(
        bottomBar = {
            if (visibilityBottomBar.value) MyBottomBar(navController = navController)
            // FIXME there is a bug, when user click "back" too fast (on screen without bar), views become half transparent
        },
        content = { padding ->
            NavHost(navController = navController, startDestination = Home) {
                //BottomNavBar
                composable<Home> {
                    HomeScreen(padding)
                }
                composable<Lists> {
//                    val listState by viewModel.listState.collectAsState()
                    val viewModel: ListViewModel = hiltViewModel()
                    val state by viewModel.listsState.collectAsState()
                    ListsScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
//                        onEvent = viewModel::onEvent,
//                        state = listState,
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        },
                        paddingValues = padding
                    )
                }
                composable<Profile> {
                    ProfileScreen(
                        isFromApproving = isTokenApproved,
                        redirectToUrl = { url ->
                            redirectToUrl(url, navController.context)
                        },
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }
                //BottomNavBar

                //Screens in Lists
                composable<Collections> {
                    HomeScreen(
                        padding = padding
                    )
                }
                composable<Rated> {
                    val viewModel: ListViewModel = hiltViewModel()
                    val state by viewModel.listsState.collectAsState()
                    RatedScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }
                composable<Favorites> {
                    val viewModel: ListViewModel = hiltViewModel()
                    val state by viewModel.listsState.collectAsState()
                    FavoritesScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }
                composable<Watchlist> {
                    val viewModel: ListViewModel = hiltViewModel()
                    val state by viewModel.listsState.collectAsState()
                    WatchlistScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }
                //Screens in Lists

                composable<CollectionDetails> {
                    val viewModel: ListViewModel = hiltViewModel()
                    val state by viewModel.collectionState.collectAsState()
                    val args = it.toRoute<CollectionDetails>()
                    ChosenCollectionScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
                        collectionId = args.collectionId,
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }
                composable<MediaDetails> {
                    HomeScreen(padding)
                }
            }
        }
    )
}

fun redirectToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

private fun NavHostController.setupDestinationListener(visibilityBottomBar: MutableState<Boolean>) {
    this.addOnDestinationChangedListener { _, destination, _ ->
//        val currentRoute = this.currentBackStackEntry?.destination?.route
        val currentRoute = destination.route
        val formattedRoute = currentRoute?.substringAfterLast(".")?.substringBefore("/") ?: "null"
        visibilityBottomBar.value = when {
            Watchlist.toString().contains(formattedRoute) -> false
            Rated.toString().contains(formattedRoute) -> false
            Favorites.toString().contains(formattedRoute) -> false
            Collections.toString().contains(formattedRoute) -> false
            CollectionDetails.toString().contains(formattedRoute) -> false
            MediaDetails.toString().contains(formattedRoute) -> false
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
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun HomeScreen(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(text = "HOME", modifier = Modifier.fillMaxWidth())
    }
}