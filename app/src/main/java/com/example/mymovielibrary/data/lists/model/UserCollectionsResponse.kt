package com.example.mymovielibrary.data.lists.model

import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.squareup.moshi.JsonClass

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
    fun isPublic() = public == 1
    return UserCollection(
        id = id,
        name = name,
        description = description,
        public = isPublic(),
        updatedAt = updated_at,
        revenue = revenue,
        averageRating = average_rating,
        itemCount = number_of_items,
        backdropPath = backdrop_path,
        posterPath = poster_path
    )
}