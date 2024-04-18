package com.example.mymovielibrary.data.lists.model

import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserListsResponse(
    val page: Int,
    val results: List<UserList>,
    val total_pages: Int,
    val total_results: Int
)

@JsonClass(generateAdapter = true)
data class UserList(
    val description: String,
    val favorite_count: Int,
    val id: Int,
    val iso_639_1: String,
    val item_count: Int,
    val list_type: String,
    val name: String,
    val poster_path: Any
)