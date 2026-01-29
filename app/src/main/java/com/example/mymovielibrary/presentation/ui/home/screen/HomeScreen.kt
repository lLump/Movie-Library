package com.example.mymovielibrary.presentation.ui.home.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovielibrary.R
import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow
import com.example.mymovielibrary.domain.image.BackdropSize
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.model.events.HomeEvent
import com.example.mymovielibrary.presentation.ui.lists.state.HomeListsState

@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit,
    state: HomeListsState,
    paddingValues: PaddingValues,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopTrendingSection(
                medias = state.trending,
                onEvent = onEvent
            )
        }

        item {
            MoviesSection(
                title = "Trending Today",
                onMovieClick = onMovieClick
            )
        }

        item {
            MoviesSection(
                title = "Popular Movies",
                onMovieClick = onMovieClick
            )
        }

        item {
            MoviesSection(
                title = "Popular TV Shows",
                onMovieClick = onMovieClick
            )
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun TopTrendingSection(
    medias: List<MediaItem>,
    onEvent: (HomeEvent) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { medias.size }
    )
    val currentIndex by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }
    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                FeaturedMoviePage(medias[page])
            }

            ScrollDotsIndicator(
                totalItems = medias.size,
                currentIndex = currentIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

        }
        //Below poster
        HorizontalDivider(Modifier
            .fillMaxWidth()
        )
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .offset(y = (-4).dp)
        ) {
            VerticalDivider(
                Modifier
                    .fillMaxHeight()
                    .padding(top = 4.dp, bottom = 4.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.titleLarge,
                text =  stringResource(R.string.trending))
            SingleChoiceButtons(
                modifier = Modifier
                    .alpha(0.7f)
                    .align(Alignment.CenterVertically),
                onChoice = {
                    when (it) {
                        0 -> onEvent(HomeEvent.LoadTrendingMedias(TimeWindow.DAY))
                        1 -> onEvent(HomeEvent.LoadTrendingMedias(TimeWindow.WEEK))
                    }
                },
                firstTextId = R.string.on_today,
                secondTextId = R.string.on_week
            )
            VerticalDivider(
                Modifier
                    .fillMaxHeight()
                    .padding(top = 4.dp, bottom = 4.dp)
            )
        }
        HorizontalDivider(Modifier
            .fillMaxWidth()
            .offset(y = (-8).dp)
        )
    }
}

@Composable
private fun FeaturedMoviePage(movie: MediaItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { /* open details */ }
    ) {
        AsyncImage(
            model = BackdropSize.W1280.url + movie.backdropPath,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // затемнение снизу
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.55f)
                        )
                    )
                )
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 46.dp, end = 16.dp)
        )
    }
}

@Composable
private fun ScrollDotsIndicator(
    totalItems: Int,
    currentIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalItems) {
            val distanceFromCenter = kotlin.math.abs(i - currentIndex)

            val targetSize = when (distanceFromCenter) {
                0 -> 10.dp
                1 -> 8.dp
                2 -> 6.dp
                3 -> 4.dp
                else -> 0.dp
            }

            val animatedSize by animateDpAsState(
                targetValue = targetSize,
                animationSpec = tween(250)
            )

            val animatedAlpha by animateFloatAsState(
                targetValue = if (i == currentIndex) 1f else 0.5f,
                animationSpec = tween(250)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(animatedSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(animatedAlpha))
            )
        }
    }
}

@Composable
private fun MoviesSection(
    title: String,
    onMovieClick: (Int) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(10) { index ->
                MoviePoster(
                    onClick = { onMovieClick(index) }
                )
            }
        }
    }
}

@Composable
private fun MoviePoster(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = "Poster",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}