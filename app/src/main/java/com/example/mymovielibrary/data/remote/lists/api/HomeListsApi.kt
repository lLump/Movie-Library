package com.example.mymovielibrary.data.remote.lists.api

import com.example.mymovielibrary.data.remote.lists.model.media.AllTrendingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HomeListsApi {
    @GET("3/trending/all/{time_window}")
    suspend fun getAllTrending(  //People includes
        @Path("time_window") timeWindow: String, // day/week
        @Header("Authorization") token: String,
    ): AllTrendingResponse

}