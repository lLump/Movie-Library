package com.example.mymovielibrary.data.lists.repository

import com.example.mymovielibrary.domain.base.repository.BaseRepository
import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.model.toUserCollection
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

class ListRepoImpl(private val api: ListApi): ListRepository, BaseRepository() {

    override suspend fun getUserCollections(): Result<List<UserCollection>, DataError> {
        return safeApiCall(errorMessage = "API User collections ERROR") {
            val response = api.getUserLists(TmdbData.accountIdV4)

            response.results.map { it.toUserCollection() }
        }
    }
}