package com.example.mymovielibrary.data.lists.model

import com.example.mymovielibrary.data.lists.adapter.MediaItemAdapter
import com.example.mymovielibrary.domain.lists.model.CollectionDetails
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale

@JsonClass(generateAdapter = true)
data class CollectionDetailsResponse(
    val average_rating: Double,
    val backdrop_path: String?,
    val created_by: CreatedBy,
    val description: String,
    val id: Int,
    val iso_3166_1: String,
    val iso_639_1: String,
    val item_count: Int,
    val name: String,
    val page: Int,
    val poster_path: String?,
    val public: Boolean,
    @Json(name = "results") @MediaItemAdapter val results: List<MediaItemResponse>,
    val revenue: Long,
    val runtime: Int,
    val sort_by: String,
    val total_pages: Int,
    val total_results: Int
)

@JsonClass(generateAdapter = true)
data class CreatedBy(
    val avatar_path: String,
    val gravatar_hash: String,
    val id: String,
    val name: String,
    val username: String
)