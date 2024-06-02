package com.example.mymovielibrary.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mymovielibrary.data.auth.repository.UserTmdbInfoImpl
import com.example.mymovielibrary.presentation.ui.theme.MyMovieLibraryTheme
import com.example.mymovielibrary.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        UserTmdbInfoImpl(this).getLocalSaveUserInfoIfExist()

        val isFromApproving = intent.scheme == "http" || intent.scheme == "https"

        setContent {
            MyMovieLibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(isFromApproving)
                }
            }
        }
    }
}