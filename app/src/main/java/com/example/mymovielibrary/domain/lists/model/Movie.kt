package com.example.mymovielibrary.domain.lists.model

data class Movie(
    override val id: Int,
    override val title: String,
    override val genreIds: List<Int>,
    override val overview: String,
    override val popularity: Double,
    override val rating: Double,
    override val rateCount: Int,
    override val originalLanguage: String,
    override val backdropPath: String,
    override val posterPath: String,
    override val adult: Boolean,
    val releaseDate: String,
) : MediaItem