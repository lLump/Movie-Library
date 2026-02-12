package com.example.mymovielibrary.data.remote.lists.api

import com.example.mymovielibrary.data.remote.lists.model.collection.UserCollectionsResponse
import com.example.mymovielibrary.data.remote.lists.model.media.AllTrendingResponse
import com.example.mymovielibrary.data.remote.lists.model.media.MoviesResponse
import com.example.mymovielibrary.data.remote.lists.model.media.TVShowsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeListsApi {
    @GET("3/trending/all/{time_window}")
    suspend fun getAllTrending(  //People includes
        @Path("time_window") timeWindow: String, // day/week
    ): AllTrendingResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MoviesResponse

    @GET("3/tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int
    ): TVShowsResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): MoviesResponse

    @GET("3/tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int
    ): TVShowsResponse

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): MoviesResponse

    @GET("3/tv/now_playing")
    suspend fun getNowPlayingTvShows(
        @Query("page") page: Int
    ): TVShowsResponse
}