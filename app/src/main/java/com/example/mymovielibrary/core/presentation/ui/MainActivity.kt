package com.example.mymovielibrary.core.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mymovielibrary.core.presentation.theme.MyMovieLibraryTheme
import com.example.mymovielibrary.core.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieLibraryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

//@Composable
//fun TestApiCalls(vm: AuthViewModel) {
//    Row(modifier = Modifier.padding(15.dp)) {
//        Button(
//            modifier = Modifier.size(150.dp),
//            onClick = { /*vm.onEvent(AuthEvent.GetToken)*/ }
//        ) {
//            Text(text = "Token")
//        }
//        Button(
//            modifier = Modifier.size(150.dp),
//            onClick = { vm::onEvent }
//        ) {
//            Text(text = "Validate session")
//        }
//        Button(
//            modifier = Modifier.size(150.dp),
//            onClick = { vm.onEvent(AuthEvent.DefaultSession) }
//        ) {
//            Text(text = "Session")
//        }
//    }
//}