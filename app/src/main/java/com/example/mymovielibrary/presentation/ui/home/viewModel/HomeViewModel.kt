package com.example.mymovielibrary.presentation.ui.home.viewModel

import androidx.lifecycle.viewModelScope
import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow
import com.example.mymovielibrary.domain.lists.repository.HomeListsRepo
import com.example.mymovielibrary.domain.model.events.HomeEvent
import com.example.mymovielibrary.domain.model.events.HomeEvent.LoadTrendingMedias
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.presentation.ui.lists.state.HomeListsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            }
        }
    }
}