package com.example.mymovielibrary.domain.lists.model

data class UserCollection(
    val id: Int,
    val name: String,
    val description: String,
    val public: Boolean,
    val updatedAt: String,
    val revenue: Long, // доход
    val averageRating: Double,
    val itemCount: Int,
    val backdropPath: String?,
    val posterPath: String?
)