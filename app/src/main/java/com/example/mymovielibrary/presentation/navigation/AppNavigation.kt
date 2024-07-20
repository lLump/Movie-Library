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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.presentation.navigation.bottomBar.MyBottomBar
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.CollectionDetails
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.Home
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.Lists
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.MediaDetails
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.Profile
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.UniversalList
import com.example.mymovielibrary.presentation.ui.lists.screen.ChosenCollectionScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.ListsScreen
import com.example.mymovielibrary.presentation.ui.lists.screen.UniversalListScreen
import com.example.mymovielibrary.presentation.ui.lists.viewModel.CollectionViewModel
import com.example.mymovielibrary.presentation.ui.lists.viewModel.DefaultListsViewModel
import com.example.mymovielibrary.presentation.ui.lists.viewModel.ListViewModel
import com.example.mymovielibrary.presentation.ui.profile.screen.ProfileScreen

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
                    val viewModel: DefaultListsViewModel = hiltViewModel()
                    val state by viewModel.listsState.collectAsState()
                    ListsScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
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

                composable<UniversalList> {
                    val args = it.toRoute<UniversalList>()
                    val listType = when (args.listType) {
                        "watchlist" -> ListType.WATCHLIST
                        "rated" -> ListType.RATED
                        "favorite" -> ListType.FAVORITE
                        else -> throw Exception("Incorrect ListType (route)")
                    }

                    val viewModel: ListViewModel = hiltViewModel()
                    val state by viewModel.listState.collectAsState()
                    UniversalListScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
                        listType = listType,
                        onBackPress = { navController.navigateUp() },
                        navigateTo = { screen ->
                            navController.navigate(screen)
                        }
                    )
                }

                composable<CollectionDetails> {
                    val args = it.toRoute<CollectionDetails>()
                    val viewModel: CollectionViewModel = hiltViewModel()
                    val state by viewModel.collectionState.collectAsState()
                    ChosenCollectionScreen(
                        onEvent = viewModel::onEvent,
                        state = state,
                        collectionId = args.collectionId,
                        onBackPress = { navController.navigateUp() },
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

private fun redirectToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

private fun NavHostController.setupDestinationListener(visibilityBottomBar: MutableState<Boolean>) {
    this.addOnDestinationChangedListener { _, destination, _ ->
//        val currentRoute = this.currentBackStackEntry?.destination?.route
        val currentRoute = destination.route
        val formattedRoute = currentRoute?.substringAfterLast(".")?.substringBefore("/") ?: "null"
        visibilityBottomBar.value = when {
//            Watchlist.toString().contains(formattedRoute) -> false
//            Rated.toString().contains(formattedRoute) -> false
//            Favorites.toString().contains(formattedRoute) -> false
            UniversalList.toString().contains(formattedRoute) -> false
            CollectionDetails.toString().contains(formattedRoute) -> false
            MediaDetails.toString().contains(formattedRoute) -> false
            else -> true
        }
    }
}
//
//@Composable
//private fun ProfileViewModel.AttachDisposableEffect(lifecycleOwner: LifecycleOwner) {
//    DisposableEffect(key1 = this) {
//        val observer = LifecycleEventObserver { source, event ->
//            when (event) {
//                Lifecycle.Event.ON_CREATE -> this@AttachDisposableEffect.onEvent(ProfileEvent.LoadUserScreen)
////                Lifecycle.Event.ON_START -> onEvent(ProfileEvent.LoadUserDetails)
//                else -> {}
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }
//}

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