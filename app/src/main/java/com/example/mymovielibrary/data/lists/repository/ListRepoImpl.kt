package com.example.mymovielibrary.data.lists.repository

import android.util.Log
import com.example.mymovielibrary.data.storage.TAG
import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.model.toUserCollection
import com.example.mymovielibrary.data.storage.TmdbData
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

class ListRepoImpl(private val api: ListApi): ListRepository {

    override suspend fun getUserCollections(): Result<List<UserCollection>, DataError.Network> {
        return try {
            val response = api.getUserLists(TmdbData.accountIdV4)

            val userCollections = response.results.map { it.toUserCollection() }
            Result.Success(userCollections)
//        } catch (e: HttpException) {
//            e.printStackTrace()
//            Result.Error(DataError.Network(e.message?: "User collections ERROR"))
        } catch (e: Exception) {
//            e.printStackTrace()
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message?: "User collections ERROR"))
        }
    }

}