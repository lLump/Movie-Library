package com.example.mymovielibrary.presentation.navigation.bar.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavController,
//    currentScreen: Screen
) {
//    when (currentScreen) {
//        Screen.HOME -> {
//
//        }
//
//        Screen.LISTS -> {
//
//        }
//
//        Screen.PROFILE -> {
//            TopAppBar(
//                title = { Text("Title") },
//                navigationIcon = {
//                    IconButton(onClick = { }) {
//                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    IconButton(onClick = { }) {
//                        Icon(Icons.Default.Settings, contentDescription = "Settings")
//                    }
//                }
//            )
//        }
//
//        else -> {}
//    }
}