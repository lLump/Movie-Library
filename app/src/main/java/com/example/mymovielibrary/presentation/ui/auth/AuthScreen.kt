package com.example.mymovielibrary.presentation.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.domain.auth.model.UserInfo
import com.example.mymovielibrary.presentation.model.ShowToast
import com.example.mymovielibrary.presentation.viewmodel.states.LoadingState
import com.example.mymovielibrary.presentation.model.UiEvent
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    event: UiEvent,
    onEvent: (AuthEvent) -> Unit,
    navigateToHome: () -> Unit,
    registration: () -> Unit
) {
    var username by remember { mutableStateOf("lLump") }
    var password by remember { mutableStateOf("bomber2002") }
    var isSaveChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.auth),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.username)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(id = R.string.password)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSaveChecked,
                onCheckedChange = { isSaveChecked = it }
            )
            Text(
                text = stringResource(id = R.string.remember_password),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(
                    AuthEvent.LoginSession(
                        user = UserInfo(
                            username = username,
                            password = password
                        ),
                        needToSave = isSaveChecked
                    )
                )
            }
        ) {
            Text(text = stringResource(id = R.string.login))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { registration() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.registration))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(AuthEvent.GuestSession)
            }
        ) {
            Text(text = stringResource(id = R.string.login_guest))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (event) {
            is UiEvent.Error -> { ShowToast(event.error.asString()) }
            is UiEvent.Loading -> {
                when (event.loading) {
                    LoadingState.SUCCESS -> navigateToHome()
                    LoadingState.EMPTY -> {}
                    LoadingState.LOADING -> CircularProgressIndicator()
                    LoadingState.FAILURE -> {}
                }
            }
        }
    }
}

