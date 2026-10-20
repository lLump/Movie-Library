package com.example.mymovielibrary.presentation.ui.details.media.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mymovielibrary.domain.details.movie.repository.MovieDetailsRepo
import com.example.mymovielibrary.domain.model.events.MediaDetailsEvent
import com.example.mymovielibrary.presentation.navigation.model.NavigationRoute
import com.example.mymovielibrary.presentation.ui.base.viewModel.BaseViewModel
import com.example.mymovielibrary.presentation.ui.details.media.state.MediaDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailsRepo: MovieDetailsRepo
    //todo provide repos
): BaseViewModel() {
    private val mediaId: Int = savedStateHandle.toRoute<NavigationRoute.MediaDetailsRoute>()
        .mediaId

    private val _mediaDetailsState = MutableStateFlow(MediaDetailsState())
    val mediaDetailsState = _mediaDetailsState.asStateFlow()

    fun onEvent(event: MediaDetailsEvent) {
        when (event) {
            is MediaDetailsEvent.LoadDetails -> viewModelScope.launch(Dispatchers.IO) {
                val mediaDetails = request { detailsRepo.getMovieDetails(mediaId) }

            }
        }
    }
}