package com.example.mymovielibrary.domain.lists.model

data class MediaItem (
    val id: Int,
    val title: String,
    val isMovie: Boolean,
    val date: String,
    val rating: Double,
    val rateCount: Int,
    val popularity: Double,
    val posterPath: String,
    val backdropPath: String
)

fun List<MediaItem>.sortedByTitle() = this.sortedBy { it.title }
fun List<MediaItem>.sortedByPopularity() = this.sortedBy { it.popularity }
fun List<MediaItem>.sortedByRating() = this.sortedBy { it.rating }
fun List<MediaItem>.sortedByRateAmount() = this.sortedBy { it.rateCount }