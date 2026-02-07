package com.example.mymovielibrary.presentation.ui.settings.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.model.events.SettingsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onEvent: (SettingsEvent) -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AdultContentBlock()

            Spacer(Modifier.height(8.dp))

            LanguageBlock()

            Spacer(Modifier.weight(1f))

            LogoutButton(modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)) {
                onEvent(SettingsEvent.Logout)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AdultContentBlock() {
    var selected by remember { mutableIntStateOf(0) }

    Card(
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp, topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                text = stringResource(R.string.adult_content),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            RadioOption(
                text = stringResource(R.string.show_immediately),
                selected = selected == 0,
                onClick = { selected = 0 }
            )

            Spacer(Modifier.height(8.dp))

            RadioOption(
                text = stringResource(R.string.hide_adult),
                selected = selected == 1,
                onClick = { selected = 1 }
            )
        }
    }
}

@Composable
private fun RadioOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageBlock() {
    val languages = listOf("Русский", "English", "Українська")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(languages.first()) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) -180f else 0f,
        animationSpec = tween(300),
        label = "ArrowRotation"
    )

    Card(
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp, topStart = 8.dp, topEnd = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Язык поиска",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                Box(
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                        .clip(if (isExpanded) RoundedCornerShape(topEnd = 26.dp, topStart = 26.dp) else RoundedCornerShape(26.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable(enabled = true, onClick = {/*Click effect*/})
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedLanguage,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            modifier = Modifier
                                .size(32.dp)
                                .rotate(arrowRotation),
                            contentDescription = null
                        )
                    }
                }
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp)
                ) {
                    languages.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                selectedLanguage = it
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LogoutButton(modifier: Modifier, onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB3261E)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = "Выйти из аккаунта",
            color = Color.White
        )
    }
}