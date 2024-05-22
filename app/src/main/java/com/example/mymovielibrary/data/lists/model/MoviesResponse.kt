package com.example.mymovielibrary.data.lists.model

import com.example.mymovielibrary.domain.lists.model.Movie
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int
)

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val media_type: String?,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
): MediaItemResponse

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        genreIds = genre_ids, //TODO получать жанры в виде строк
        overview = overview,
        popularity = popularity,
        rating = vote_average,
        rateCount = vote_count,
        originalLanguage = original_language,
        releaseDate = release_date,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        adult = adult,
    )
}