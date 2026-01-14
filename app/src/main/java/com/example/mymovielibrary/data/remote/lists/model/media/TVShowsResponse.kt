package com.example.mymovielibrary.data.remote.lists.model.media

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVShowsResponse(
    val page: Int,
    val results: List<TVShowResponse>,
    val total_pages: Int,
    val total_results: Int
)

@JsonClass(generateAdapter = true)
data class TVShowResponse(
    val media_type: String?,
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
): MediaItemResponse