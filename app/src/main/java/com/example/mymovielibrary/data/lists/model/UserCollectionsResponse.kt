package com.example.mymovielibrary.data.lists.model

import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.squareup.moshi.JsonClass
import java.util.Locale

@JsonClass(generateAdapter = true)
data class UserCollectionsResponse(
    val page: Int,
    val results: List<CollectionResponse>,
    val total_pages: Int,
    val total_results: Int
)

@JsonClass(generateAdapter = true)
data class CollectionResponse(
    val account_object_id: String,
    val adult: Int,
    val average_rating: Double,
    val backdrop_path: String?,
    val created_at: String,
    val description: String,
    val featured: Int,
    val id: Int,
    val iso_3166_1: String,
    val iso_639_1: String,
    val name: String,
    val number_of_items: Int,
    val poster_path: String?,
    val public: Int,
    val revenue: Long,
    val runtime: String,
    val sort_by: Int,
    val updated_at: String
)

fun CollectionResponse.toUserCollection(): UserCollection {
    fun formatTime(min: Int): Pair<String, String> {
        val hours = (min / 60).toString()
        val remainingMin = (min % 60).toString()
        return Pair(hours, remainingMin)
    }
    fun formatNumber(number: Long) = when {
        number >= 1_000_000_000 -> String.format(Locale.US, "%.1fB", number / 1_000_000_000.0)
        number >= 1_000_000 -> String.format(Locale.US, "%.1fM", number / 1_000_000.0)
        number >= 1_000 -> String.format(Locale.US, "%.1fK", number / 1_000.0)
        else -> number.toString()
    }

    return UserCollection(
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
}