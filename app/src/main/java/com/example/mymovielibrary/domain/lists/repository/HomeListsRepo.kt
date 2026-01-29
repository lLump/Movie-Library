package com.example.mymovielibrary.domain.lists.repository

import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface HomeListsRepo {
    suspend fun getAllTrending(timeWindow: TimeWindow): Result<List<MediaItem>, DataError>
}