package com.example.mymovielibrary.data.account.model

import com.example.mymovielibrary.domain.account.model.LanguageDetails
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LanguageResponse(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)

fun LanguageResponse.toLanguageDetails(): LanguageDetails {
    return LanguageDetails(
        name = english_name,
        iso = iso_639_1
    )
}