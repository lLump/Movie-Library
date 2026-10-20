package com.example.mymovielibrary.domain.details.movie.model

data class MovieDetails(
    val isAdult: Boolean,
    val backdropPhotoUrl: String,
    val posterPhotoUrl: String,
    val originalName: String,
    val name: String,
    val overview: String,
    val tagline: String,
//    val genres
    val releaseDate: String,
    val budget: Int,
    val revenue: Int,
    val runtime: Int, //in minutes
    val status: String,
    val popularity: Double,
    val rating: Double,
    val rateCount: Int
)
