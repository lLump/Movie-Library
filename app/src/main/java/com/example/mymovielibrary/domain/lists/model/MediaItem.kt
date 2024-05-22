package com.example.mymovielibrary.domain.lists.model

interface MediaItem {
    val id: Int
    val title: String
    val genreIds: List<Int>
    val overview: String
    val popularity: Double
    val rating: Double
    val rateCount: Int
    val originalLanguage: String
    val backdropPath: String
    val posterPath: String
    val adult: Boolean
}

fun List<MediaItem>.sortedByTitle() = this.sortedBy { it.title }
fun List<MediaItem>.sortedByPopularity() = this.sortedBy { it.popularity }
fun List<MediaItem>.sortedByRating() = this.sortedBy { it.rating }
fun List<MediaItem>.sortedByRateAmount() = this.sortedBy { it.rateCount }
