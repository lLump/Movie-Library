package com.example.mymovielibrary.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mymovielibrary.data.local.LocalInfoManagerImpl
import com.example.mymovielibrary.presentation.navigation.AppNavigation
import com.example.mymovielibrary.presentation.ui.theme.MyMovieLibraryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        LocalInfoManagerImpl(this).loadUserInfoFromPrefsToSingletonIfExist()

        setContent {
            MyMovieLibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        isTokenApproved = intent.scheme == "http" || intent.scheme == "https"
                    )
                }
            }
        }
    }
}