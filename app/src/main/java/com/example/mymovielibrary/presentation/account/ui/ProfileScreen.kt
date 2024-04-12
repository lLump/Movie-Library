@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymovielibrary.presentation.account.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.R

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
//    Scaffold(
//        modifier = Modifier.padding(8.dp),
//        content = { padding ->
//             ProfileContent(
//                 Modifier
//                     .fillMaxSize()
//                     .padding(padding)
//             )
//                  },
// //       topBar = { TopAppBar(title = {
// //          Text(
// //               text = "Профиль",
// //           )
// //       })
///*    }) */ )
    Column(modifier = Modifier.fillMaxSize()) {
        ProfileCard()
        DropdownLanguageMenu()
    }
}

@Composable
fun DropdownLanguageMenu() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        "Настройки",
        "Выход",
        "sadsa",
    )

    Box {
        Text(
            text = "Choose language",
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
            items.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        // Обработка нажатия на элемент списка
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun ProfileCard() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement =
    ) {
        Image(
            modifier = Modifier
                .weight(0.45f)
                .padding(16.dp),
//                .size(150.dp, 140.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Photo",
//            modifier = Modifier
//                .size(120.dp)
//                .clip(shape = CircleShape)
//                .padding(start = 16.dp, top = 16.dp)
        )
        Column(modifier = Modifier.padding(start = 75.dp).weight(1f)) {
            Text(text = "Username")
            Text(text = "Full name")
        }
    }
}