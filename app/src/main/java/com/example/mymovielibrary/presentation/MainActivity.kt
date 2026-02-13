package com.example.mymovielibrary.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mymovielibrary.presentation.navigation.AppNavigation
import com.example.mymovielibrary.presentation.ui.theme.MyMovieLibraryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        if (isFromApproving(intent.scheme)) { viewModel.onTokenApprove() }

        setContent {
            val authState by viewModel.authState.collectAsState()
            MyMovieLibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(authState)
                }
            }
        }
    }

    private fun isFromApproving(scheme: String?): Boolean = scheme == "mymovielibrary"
}