@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymovielibrary.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileState

@Composable
fun ProfileScreen(
    onEvent: (ProfileEvent) -> Unit,
    state: ProfileState
) {
    LaunchedEffect(Unit) {
        onEvent(ProfileEvent.LoadProfile)
        onEvent(ProfileEvent.LoadLanguages)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ProfileCard(state)
        DropdownLanguageMenu(state.listLanguages, onEvent)
    }
}

@Composable
fun DropdownLanguageMenu(languagesList: List<LanguageDetails>, onEvent: (ProfileEvent) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = stringResource(id = R.string.choose_language),
            modifier = Modifier
                .padding(8.dp)
                .clickable { expanded = true },
            color = Color.Blue
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterStart)
        ) {
            languagesList.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Row {
                            Text(text = language.name)
                            //                            Spacer(modifier = Modifier.width(4.dp))
                            //                            Divider(color = Color.Black, modifier = Modifier.fillMaxHeight())
                            //                            Spacer(modifier = Modifier.width(4.dp))
                            //                            Text(text = language.iso)
                        }
                    },
                    onClick = {
                        onEvent(ProfileEvent.SaveLanguage(language))
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun ProfileCard(state: ProfileState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement =
    ) {
//        Image(
//            modifier = Modifier
//                .weight(0.45f)
//                .padding(16.dp),
////                .size(150.dp, 140.dp),
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = "Profile Photo",
////            modifier = Modifier
////                .size(120.dp)
////                .clip(shape = CircleShape)
////                .padding(start = 16.dp, top = 16.dp)
//        )
        Image(
            bitmap = state.user.avatar,
            contentDescription = "Profile photo",
            modifier = Modifier
                .padding(16.dp)
                .clip(shape = CircleShape)
////                .size(150.dp, 140.dp),
////            modifier = Modifier
////                .size(120.dp)
////                .padding(start = 16.dp, top = 16.dp)
//        )
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Row {
                Text(text = stringResource(id = R.string.username) + ": ")
                state.user.username.let { Text(text = it) }
            }
            Row {
                Text(text = stringResource(id = R.string.full_name) + ": ")
                state.user.name.let { Text(text = it) }
            }
        }
    }
}