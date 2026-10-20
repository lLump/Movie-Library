package com.example.mymovielibrary.data.remote.details.movie.model

import com.example.mymovielibrary.domain.details.movie.model.MovieDetails
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val softcore: Boolean,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

fun MovieDetailsResponse.toMovieDetails(): MovieDetails {
    return MovieDetails(
        isAdult = adult,
        backdropPhotoUrl = backdrop_path,
        posterPhotoUrl = poster_path,
        originalName = original_title,
        name = title,
        overview = overview,
        tagline = tagline,
//        genres = ,
        releaseDate = release_date,
        budget = budget,
        revenue = revenue,
        runtime = runtime,
        status = status,
        popularity = popularity,
        rating = vote_average,
        rateCount = vote_count
    )
}