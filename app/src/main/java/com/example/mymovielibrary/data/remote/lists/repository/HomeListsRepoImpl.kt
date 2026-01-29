package com.example.mymovielibrary.data.remote.lists.repository

import com.example.mymovielibrary.data.local.storage.Store
import com.example.mymovielibrary.data.remote.base.repository.BaseRepository
import com.example.mymovielibrary.data.remote.lists.api.HomeListsApi
import com.example.mymovielibrary.data.remote.lists.model.enums.TimeWindow
import com.example.mymovielibrary.data.remote.lists.util.toMedias
import com.example.mymovielibrary.domain.lists.model.MediaItem
import com.example.mymovielibrary.domain.lists.repository.HomeListsRepo
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

class HomeListsRepoImpl(private val api: HomeListsApi): HomeListsRepo, BaseRepository() {

    override suspend fun getAllTrending(timeWindow: TimeWindow): Result<List<MediaItem>, DataError> {
        return safeApiCall(errorMessage = "API All trending ERROR") {
            val response = api.getAllTrending(timeWindow.value, accessToken)

            response.toMedias()
        }
    }
}