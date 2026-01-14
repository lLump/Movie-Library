package com.example.mymovielibrary.data.remote.lists.model.media

import com.example.mymovielibrary.domain.lists.model.Movie
import com.example.mymovielibrary.domain.lists.model.TVShow

fun TVShowResponse.toTvShow(): TVShow {
    return TVShow(
        id = id,
        title = name,
        genreIds = genre_ids, //TODO получать жанры в виде строк
        description = overview,
        popularity = popularity,
        rating = vote_average,
        rateCount = vote_count,
        originalLanguage = original_language,
        originalCountries = origin_country,
        date = first_air_date,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        adult = adult,
    )
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        genreIds = genre_ids, //TODO получать жанры в виде строк
        description = overview,
        popularity = popularity,
        rating = vote_average,
        rateCount = vote_count,
        originalLanguage = original_language,
        date = release_date,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        adult = adult,
    )
}