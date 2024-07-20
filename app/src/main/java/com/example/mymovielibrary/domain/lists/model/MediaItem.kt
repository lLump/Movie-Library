package com.example.mymovielibrary.domain.lists.model

sealed interface MediaItem {
    val isMovie: Boolean
    val id: Int
    val title: String
    val genreIds: List<Int>
    val description: String
    val popularity: Double
    val rating: Double
    val rateCount: Int
    val originalLanguage: String
    val backdropPath: String
    val posterPath: String
    val adult: Boolean
    val date: String
}

fun List<MediaItem>.sortedByTitle() = this.sortedBy { it.title }
fun List<MediaItem>.sortedByPopularity() = this.sortedBy { it.popularity }
fun List<MediaItem>.sortedByRating() = this.sortedBy { it.rating }
fun List<MediaItem>.sortedByRateAmount() = this.sortedBy { it.rateCount }

data class TVShow(
    override val isMovie: Boolean = false,
    override val id: Int,
    override val title: String,
    override val genreIds: List<Int>,
    override val description: String,
    override val popularity: Double,
    override val rating: Double,
    override val rateCount: Int,
    override val originalLanguage: String,
    override val backdropPath: String,
    override val posterPath: String,
    override val adult: Boolean,
    override val date: String,
    val originalCountries: List<String>,
) : MediaItem

data class Movie(
    override val isMovie: Boolean = true,
    override val id: Int,
    override val title: String,
    override val genreIds: List<Int>,
    override val description: String,
    override val popularity: Double,
    override val rating: Double,
    override val rateCount: Int,
    override val originalLanguage: String,
    override val backdropPath: String,
    override val posterPath: String,
    override val adult: Boolean,
    override val date: String,
) : MediaItem