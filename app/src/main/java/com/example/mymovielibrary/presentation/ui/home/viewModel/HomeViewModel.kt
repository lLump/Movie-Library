package com.example.mymovielibrary.presentation.ui.home.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow
import com.example.mymovielibrary.domain.lists.model.sortedByPopularity
import com.example.mymovielibrary.domain.lists.model.sortedByRateAmount
import com.example.mymovielibrary.domain.lists.model.sortedByRating
import com.example.mymovielibrary.domain.lists.repository.HomeListsRepo
import com.example.mymovielibrary.domain.model.events.HomeEvent
import com.example.mymovielibrary.domain.model.events.HomeEvent.*
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.presentation.ui.lists.state.HomeListsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listsRepo: HomeListsRepo,
): BaseViewModel() {

    init {
        loadTrending(TimeWindow.DAY)
        loadScreen()
    }

    private val _listsState = MutableStateFlow(HomeListsState())
    val listsState = _listsState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is LoadTrendingMedias -> loadTrending(event.timeWindow)
        }
    }

    private fun loadTrending(timeWindow: TimeWindow) {
        viewModelScope.launch {
            val medias = withContext(Dispatchers.IO) {
                request { listsRepo.getAllTrending(timeWindow) } ?: emptyList()
            }
            _listsState.update { state ->
                state.copy(trending = medias)
                //fixme (убрать copy, так как на экране много информации которая будет копироваться за зря. В идеале оставить один state (опционально)
            }
        }
    }

    private fun loadScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val popularMovies = async { request { listsRepo.getPopularMovies() }.orEmpty() }
            val popularTvShows = async { request { listsRepo.getPopularTvShows() }.orEmpty() }

            val topRatedMovies = async { request { listsRepo.getTopRatedMovies() }.orEmpty() }
            val topRatedTvShows = async { request { listsRepo.getTopRatedTvShows() }.orEmpty() }

            val nowPlayingMovies = async { request { listsRepo.getNowPlayingMovies() }.orEmpty() }
            val nowPlayingShows = async { request { listsRepo.getNowPlayingTvShows() }.orEmpty() }

            withContext(Dispatchers.Main) {
                _listsState.update { state ->
                    state.copy(
                        popular = (popularMovies.await() + popularTvShows.await()).sortedByPopularity(),
                        topRated = (topRatedMovies.await() + topRatedTvShows.await()).sortedByRating(),
                        nowPlaying = (nowPlayingMovies.await() + nowPlayingShows.await()).sortedByRateAmount()
                    )
                }
            }
        }
    }
}