package com.example.mymovielibrary.domain.lists.model

data class CollectionDetails(
    val id: Int,
    val name: String,
    val description: String,
    val movies: List<MediaItem>,
    val averageRating: String,
    val runtime: Pair<String, String>,
    val revenue: String,
    val backdropPath: String?,
    val itemsCount: String,
    val public: Boolean
//    val sortBy: String,
)
