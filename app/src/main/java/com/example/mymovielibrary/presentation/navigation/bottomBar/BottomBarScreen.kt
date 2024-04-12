package com.example.mymovielibrary.presentation.navigation.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mymovielibrary.presentation.navigation.NavigationRoute
import com.example.mymovielibrary.presentation.navigation.Screen

sealed class BottomBarScreen(
    var route: String,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector
) {
    data object Home: BottomBarScreen(
        route = Screen.HOME(),
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    data object Lists: BottomBarScreen(
        route = Screen.LISTS(),
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List
    )
    data object Profile: BottomBarScreen(
        route = Screen.PROFILE(),
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle
    )
}