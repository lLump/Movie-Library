package com.example.mymovielibrary.domain.lists.model

data class UserCollection(
    val id: Int,
    val name: String,
    val description: String,
    val public: Boolean,
    val updatedAt: String,
    val runtime: Pair<String, String>, // hours & minutes
    val revenue: String, // доход
    val averageRating: String,
    val itemsCount: String,
    val backdropPath: String?,
//    val posterPath: String?
)