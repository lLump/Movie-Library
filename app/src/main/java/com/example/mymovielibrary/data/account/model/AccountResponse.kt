package com.example.mymovielibrary.data.account.model

data class AccountDetails(
    val avatar: Avatar,
    val id: Int,
    val include_adult: Boolean,
    val iso_3166_1: String,
    val iso_639_1: String,
    val name: String,
    val username: String
)

data class Avatar(
    val gravatar: Gravatar,
    val tmdb: Tmdb
)

data class Gravatar(
    val hash: String
)

data class Tmdb(
    val avatar_path: String
)