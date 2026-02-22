package com.example.mymovielibrary.data.remote.lists.util

import com.example.mymovielibrary.data.remote.lists.model.media.MediaItemResponse
import com.example.mymovielibrary.data.remote.lists.model.media.MovieResponse
import com.example.mymovielibrary.data.remote.lists.model.media.TVShowResponse
import com.example.mymovielibrary.domain.lists.model.MediaItem


fun MediaItemResponse.toMediaItem(): MediaItem {
    return when (this) {
        is MovieResponse -> this.toMediaItem()
        is TVShowResponse -> this.toMediaItem()
        else -> throw Exception("Unreal mediaType exception")

    }
}

fun MovieResponse.toMediaItem(): MediaItem {
    return MediaItem(
        id = id,
        title = title,
        isMovie = true,
        popularity = popularity,
        rating = vote_average,
        rateCount = vote_count,
        date = release_date,
        backdropPath = backdrop_path,
        posterPath = poster_path
    )
}

fun TVShowResponse.toMediaItem(): MediaItem {
    return MediaItem(
        id = id,
        title = name,
        isMovie = false,
        popularity = popularity,
        rating = vote_average,
        rateCount = vote_count,
        date = first_air_date,
        backdropPath = backdrop_path,
        posterPath = poster_path
    )
}