package com.example.mymovielibrary.presentation.ui.profile.screen

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.image.ProfileSize
import com.example.mymovielibrary.domain.lists.model.enums.ListType
import com.example.mymovielibrary.domain.model.events.AccountEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.Lists
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute.UniversalList
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileDisplay
import com.example.mymovielibrary.presentation.ui.profile.state.ProfileState
import com.example.mymovielibrary.presentation.ui.profile.state.UserStats
import com.example.mymovielibrary.presentation.ui.profile.state.UserType
import com.example.mymovielibrary.presentation.ui.theme.Typography
@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (AccountEvent) -> Unit,
    redirectToUrl: (String) -> Unit,
    navigateTo: (NavigationRoute) -> Unit,
    isFromApproving: Boolean
) {
    LaunchedEffect(Unit) {
        if (isFromApproving) {                // check if user from login
            onEvent(AccountEvent.ApproveToken)
        }
        onEvent(AccountEvent.LoadUserScreen)
    }

//    when (uiEvents) {
//        is Error -> ShowToast(message = (uiEvents as Error).error.asString())
//        Initial -> {}
//    }

    // Screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state.userDetails) {
            is UserType.LoggedIn -> {
                UserProfile(state.userDetails.profile, state.userStats, navigateTo)
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { navigateTo(NavigationRoute.Settings) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings screen"
                    )
                }
            }

            UserType.Guest -> {
                GuestProfile(
                    onLoginClick = { onEvent(AccountEvent.Login) },
                    onRegisterClick = { redirectToUrl("https://www.themoviedb.org/signup") }
                )
            }

            UserType.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            }

            is UserType.NeedApproval -> {
                redirectToUrl("https://www.themoviedb.org/auth/access?request_token=${state.userDetails.requestToken}")
            }
        }
//            IconButton(
//                modifier = Modifier.align(Alignment.TopStart),
//                onClick = { }
//            ) {
//                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
//            }
    }
}

@Composable
private fun UserProfile(user: ProfileDisplay, stats: UserStats, toScreen: (NavigationRoute) -> Unit) {
    Column {
        ProfileCard(
            user = user,
            stats = stats,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                )
                .padding(bottom = 5.dp) //доп layout снизу (баг?)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                )
                .padding(top = 5.dp)
                .background(
                    color = (MaterialTheme.colorScheme.onSecondary),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
        ) {
            val btnModifier = Modifier
                .weight(0.25f)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                .alpha(0.5f)
            ListButton(modifier = btnModifier, textId = R.string.my_lists, icon = Icons.AutoMirrored.Filled.List) {
                toScreen(Lists)
            }
            ListButton(modifier = btnModifier, textId = R.string.watchlist, icon = Icons.Default.Bookmarks) {
                toScreen(UniversalList(ListType.WATCHLIST.route))
            }
            ListButton(modifier = btnModifier, textId = R.string.rated, icon = Icons.Default.Star) {
                toScreen(UniversalList(ListType.RATED.route))
            }
            ListButton(modifier = btnModifier,textId = R.string.favorite, icon = Icons.Default.Favorite) {
                toScreen(UniversalList(ListType.FAVORITE.route))
            }
            Spacer(modifier = Modifier.weight(0.8f))
        }
        //TODO сделать цвет такой же как и фон коламна
        Spacer(modifier = Modifier //instead of bottomBar padding
            .weight(0.107f)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSecondary)
        )
    }
}

@Composable
private fun ListButton(modifier: Modifier, @StringRes textId: Int, icon: ImageVector, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp),
        colors = ButtonDefaults.filledTonalButtonColors(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Some icon list",
//                tint = Color.White,
                modifier = Modifier
                    .padding(top = 11.dp, bottom = 11.dp)
                    .aspectRatio(1f)
                    .fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(16.dp)) //отступ между текстом и иконкой
            Text(text = stringResource(id = textId), style = Typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Right arrow to some List",
            )
        }
    }
}

@Composable
private fun ProfileCard(modifier: Modifier, user: ProfileDisplay, stats: UserStats) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
                model = ProfileSize.ORIGINAL.url + user.avatarPath,
                contentDescription = "Profile photo",
            )
            Column(
                modifier = Modifier
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
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        MiniUserStat(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .weight(0.25f),
            stats = stats
        )
    }
}

@Composable
private fun MiniUserStat(
    modifier: Modifier,
    stats: UserStats,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SingleUserStat(textId = R.string.watched, amount = stats.watched)
        VerticalDivider(modifier = Modifier.fillMaxHeight())

        SingleUserStat(textId = R.string.watchlist, amount = stats.planned)
        VerticalDivider(modifier = Modifier.fillMaxHeight())

        SingleUserStat(textId = R.string.rated, amount = stats.rated)
        VerticalDivider(modifier = Modifier.fillMaxHeight())

        SingleUserStat(textId = R.string.favorite, amount = stats.favorite)
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
            style = Typography.bodyMedium,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = amount,
            style = Typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(0.6f),
        )
    }
}

@Composable
private fun GuestProfile(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.not_logged_in),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.please_login),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onLoginClick) {
                    Text(text = stringResource(id = R.string.login))
                }
                Button(onClick = onRegisterClick) {
                    Text(text = stringResource(id = R.string.registration))
                }
            }
        }
    }
}

@Composable
fun ShowProgress(score: Int) {
    val gradient = Brush.linearGradient(
        listOf(
            Color(0xFFF95075),
            Color(0xFFBE6BE5)
        )
    )

    val progressFactor by remember(score) {
        mutableStateOf(score * 0.005f)
    }

    Row(
        modifier = Modifier
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
            )
        ) {

            Text(
                text = score.toString(),
//                modifier = Modifier
//                    .clip(shape = RoundedCornerShape(23.dp))
//                    .fillMaxHeight(0.87f)
//                    .fillMaxWidth()
//                    .padding(7.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
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