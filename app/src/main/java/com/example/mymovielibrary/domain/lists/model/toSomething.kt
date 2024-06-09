package com.example.mymovielibrary.domain.lists.model

import com.example.mymovielibrary.data.lists.model.CollectionDetailsResponse
import com.example.mymovielibrary.data.lists.model.CollectionResponse
import com.example.mymovielibrary.data.lists.model.MovieResponse
import com.example.mymovielibrary.data.lists.model.TVShowResponse
import com.example.mymovielibrary.data.lists.model.toMovie
import com.example.mymovielibrary.data.lists.model.toTvShow
import java.util.Locale

fun CollectionResponse.toUserCollection() = UserCollection(
    id = id,
    name = name,
    description = description,
    public = public == 1,
    updatedAt = updated_at,
    revenue = formatNumber(revenue),
    runtime = formatTime(runtime.toInt()),
    averageRating = average_rating.toString(),
    itemsCount = number_of_items.toString(),
    backdropPath = backdrop_path,
//        posterPath = poster_path
)


fun CollectionDetailsResponse.toCollectionDetails(): CollectionDetails {
    val movies = results.map {
        when (it) {
            is MovieResponse -> it.toMovie()
//            is TVShowResponse -> it.toTvShow()
            else -> { (it as TVShowResponse).toTvShow()}
        }
    }
    return CollectionDetails(
        id = id,
        name = name,
        movies = movies,
        description = description,
        public = public,
        revenue = formatNumber(revenue),
        runtime = formatTime(runtime),
        averageRating = average_rating.toString(),
        itemsCount = item_count.toString(),
        backdropPath = backdrop_path,
    )
}

private fun formatTime(min: Int): Pair<String, String> {
    val hours = (min / 60).toString()
    val remainingMin = (min % 60).toString()
    return Pair(hours, remainingMin)
}


private fun formatNumber(number: Long) = when {
    number >= 1_000_000_000 -> String.format(Locale.US, "%.1fB", number / 1_000_000_000.0)
    number >= 1_000_000 -> String.format(Locale.US, "%.1fM", number / 1_000_000.0)
    number >= 1_000 -> String.format(Locale.US, "%.1fK", number / 1_000.0)
    else -> number.toString()
}