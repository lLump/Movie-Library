package com.example.mymovielibrary.domain.model.events

import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow

sealed interface HomeEvent {
    data class LoadTrendingMedias(val timeWindow: TimeWindow): HomeEvent
}