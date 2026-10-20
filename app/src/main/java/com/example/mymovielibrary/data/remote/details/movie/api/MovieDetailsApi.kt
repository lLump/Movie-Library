package com.example.mymovielibrary.data.remote.details.movie.api

import com.example.mymovielibrary.data.remote.details.movie.model.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApi {
    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int
    ): MovieDetailsResponse
}