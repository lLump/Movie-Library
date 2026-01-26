package com.example.mymovielibrary.data.remote.lists.model.media

import com.example.mymovielibrary.domain.lists.model.MediaItem

fun MovieResponse.toMediaUI(): MediaItem {
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

fun TVShowResponse.toMediaUI(): MediaItem {
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