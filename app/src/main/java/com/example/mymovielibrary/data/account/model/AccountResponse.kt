package com.example.mymovielibrary.data.account.model

import com.example.mymovielibrary.domain.account.model.ProfileDetails
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountDetails(
    val avatar: Avatar,
    val id: Int,
    val include_adult: Boolean,
    val iso_3166_1: String,
    val iso_639_1: String,
    val name: String,
    val username: String
)

@JsonClass(generateAdapter = true)
data class Avatar(
    val gravatar: Gravatar,
    val tmdb: Tmdb
)

@JsonClass(generateAdapter = true)
data class Gravatar(
    val hash: String
)

@JsonClass(generateAdapter = true)
data class Tmdb(
    val avatar_path: String
)

fun AccountDetails.toProfileDetails(): ProfileDetails {
    return ProfileDetails(
        name = this.name,
        username = this.username,
        avatarPath = avatar.tmdb.avatar_path,
        languageIso = iso_639_1
    )
}