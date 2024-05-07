package com.example.mymovielibrary.presentation.ui.profile

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.example.mymovielibrary.domain.model.events.AuthEvent
import com.example.mymovielibrary.domain.model.events.Event
import com.example.mymovielibrary.domain.model.events.ProfileEvent
import com.example.mymovielibrary.presentation.ui.theme.MyMovieLibraryTheme
import com.example.mymovielibrary.presentation.ui.theme.Purple40
import com.example.mymovielibrary.presentation.ui.theme.Typography
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileDisplay
import com.example.mymovielibrary.presentation.viewmodel.states.ProfileState
import com.example.mymovielibrary.util.GetVibrantColorFromPoster

@Composable
fun ProfileScreen(
    onEvent: (Event) -> Unit,
    profile: ProfileState,
    padding: PaddingValues,
) {
    LaunchedEffect(Unit) {
        onEvent(ProfileEvent.LoadProfile)
    }
//    val uiState = profileViewModel.uiState.collectAsState().value

//    when {
//        uiState.loading -> {
//            val title = stringResource(id = R.string.fetching_profile)
//            LoadingColumn(title)
//        }
//        uiState.error != null -> {
//            ErrorColumn(uiState.error.message.orEmpty())
//        }
//        uiState.profile != null -> {
//            Profile(uiState.profile)
//        }
//    }
//    Profile(state.user, onEvent, padding)
    val colors = MaterialTheme.colorScheme
    var screenHeight by remember { mutableIntStateOf(0) }
    var avatarHeight by remember { mutableIntStateOf(0) }

//    Surface(
//        modifier = Modifier
//            .onSizeChanged { screenHeight = it.height }
//            .fillMaxSize()
//            .padding(padding)
//    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                ProfileCard(
                    user = profile.user,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                        )
                        .padding(bottom = 4.dp) //доп layout снизу (баг?)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                        )
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.7f)
                        .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                        .background((Color.Yellow), RoundedCornerShape(16.dp))
                ) {

                }
            }
        }

}

@Composable
fun ProfileCard(modifier: Modifier, user: ProfileDisplay) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
//            modifier = modifier
//                .fillMaxSize()
//                .weight(0.9f)
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(16.dp)
//                            .weight(0.4f)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
//                        contentScale = ContentScale.Crop,
                bitmap = user.avatar.asImageBitmap(),
                contentDescription = "Profile photo",
            )
            Column(
                modifier = Modifier
//                            .weight(0.6f)
                    .align(Alignment.CenterVertically),
            ) {
                Text(
                    style = Typography.headlineSmall,
                    text = stringResource(id = R.string.user_welcome)
                )
                Text(
                    style = Typography.headlineLarge,
                    text = "   ${user.username}"
                )
            }
        }

        MiniUserStat(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .weight(0.25f),
            watched = "6", planned = "12", rated = "4", favorite = "2", total = "18"
        )
//        Spacer(modifier = Modifier.height(4.dp))
//                ShowProgress(75)
    }
}

@Composable
private fun MiniUserStat(
    modifier: Modifier,
    watched: String,
    planned: String,
    rated: String,
    favorite: String,
    total: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SingleUserStat(textId = R.string.watched, amount = watched)
        VerticalDivider(modifier = Modifier.fillMaxHeight())

        SingleUserStat(textId = R.string.planned, amount = planned)
        VerticalDivider(modifier = Modifier.fillMaxHeight())

        SingleUserStat(textId = R.string.rated, amount = rated)
        VerticalDivider(modifier = Modifier.fillMaxHeight())

        SingleUserStat(textId = R.string.favorite, amount = favorite)
//        Text(stringResource(id = R.string.total))
    }
}

@Composable
private fun SingleUserStat(@StringRes textId: Int, amount: String) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(id = textId),
            style = Typography.bodyMedium
        )
        Text(
            text = amount,
            style = Typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ShowProgress(score : Int){
    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val progressFactor by remember(score) {
        mutableStateOf(score*0.005f)
    }

    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(35.dp)
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(color = Color.DarkGray.copy(0.5f)),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )) {

            Text(text = score.toString(),
//                modifier = Modifier
//                    .clip(shape = RoundedCornerShape(23.dp))
//                    .fillMaxHeight(0.87f)
//                    .fillMaxWidth()
//                    .padding(7.dp),
                color= Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Profile(profile: ProfileDisplay, onEvent: (Event) -> Unit, padding: PaddingValues) =
    MyMovieLibraryTheme {
//    val defaultVibrantColor = MaterialTheme.colorScheme.onSurface
//    val vibrantColor = remember { Animatable(defaultVibrantColor) }
        var screenHeight by remember { mutableIntStateOf(0) }
        var imageHeight by remember { mutableIntStateOf(0) }
        Surface(Modifier
            .fillMaxSize()
            .onSizeChanged { screenHeight = it.height }
            .padding(padding)) {

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
            ) {
            }
            Image(
                bitmap = profile.avatar.asImageBitmap(),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { imageHeight = it.height },
            )
            GetVibrantColorFromPoster(profile.avatar, Animatable(Purple40))
            val imageHeightToScreenHeightRatio = try {
                (imageHeight / screenHeight).toFloat()
                    .coerceIn(minimumValue = 0.4f, maximumValue = 0.7f)
            } catch (e: Exception) {
                0.4f
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Purple40.copy(alpha = 0f),
                            imageHeightToScreenHeightRatio - 0.05f to Purple40,
                        ),
                    ),
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            ) {
                Spacer(Modifier.fillMaxHeight(imageHeightToScreenHeightRatio - 0.2f))
                Text(
                    text = profile.name,
                    style = Typography.bodyLarge.copy(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.5.sp,
                        fontFamily = FontFamily.Cursive,
                        color = Color.White.copy(alpha = 0.8f),
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                ) {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Button(onClick = { onEvent(AuthEvent.LoginSession) }) {
                            Text(stringResource(id = R.string.login))
                        }
                        ProfileField(R.string.user_welcome, profile.username)
                        AlsoKnownAs(listOf("some", "stupid", "info"), Purple40)
                        ImdbProfileButton(profile.avatar, Purple40)
                        Text(profile.languageIso, Modifier.padding(top = 12.dp))
                    }
                }
            }
        }
    }

@Composable
private fun ProfileField(@StringRes resId: Int, field: String) {
    if (field.isEmpty()) return

    Text(stringResource(resId, field), style = Typography.bodyLarge)
}

@Composable
private fun AlsoKnownAs(alsoKnownAs: List<String>, vibrantColor: Color) {
    if (alsoKnownAs.isEmpty()) return

    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        item {
            Text("Так же известен как:", style = Typography.bodyMedium)
        }
        items(alsoKnownAs) {
            Text(
                it,
                style = Typography.bodySmall,
                modifier = Modifier
                    .border(1.25.dp, vibrantColor, RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
            )
        }
    }
}

@Composable
private fun ImdbProfileButton(imdbProfileUrl: Bitmap, currentVibrantColor: Color) {
    val context = LocalContext.current
    Row(
        Modifier
//            .clickable { imdbProfileUrl.openInChromeCustomTab(context, currentVibrantColor) }
            .padding(all = 4.dp),
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.Send, //OpenInNew
            contentDescription = "Открыть в TMDB",
            tint = currentVibrantColor,
            modifier = Modifier.scale(1.1f),
        )
        Text(
            "Открыть в TMDB",
            Modifier.padding(start = 8.dp),
            color = currentVibrantColor,
        )
    }
}
//    when (uiEvent) {
//        is UiEvent.Error -> { ShowToast(uiEvent.error.asString()) }
//        is UiEvent.Loading -> {
//            when (uiEvent.loading) {
//                LoadingState.SUCCESS -> approveToken()
//                LoadingState.EMPTY -> {}
//                LoadingState.LOADING -> CircularProgressIndicator()
//                LoadingState.FAILURE -> {}
//            }
//        }
//    }

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