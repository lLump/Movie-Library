package com.example.mymovielibrary.domain.lists.model

enum class SortType(val type: String) {
    ORIGINAL_ASCENDING("original_order.asc"),
    ORIGINAL_DESCENDING("original_order.desc"),
    RATING_ASCENDING("vote_average.asc"),
    RATING_DESCENDING("vote_average.desc"),
    RELEASE_ASCENDING("release_date.asc"),
    RELEASE_DESCENDING("release_date.desc"),
    PRIMARY_RELEASE_ASCENDING("primary_release_date.asc"),
    PRIMARY_RELEASE_DESCENDING("primary_release_date.desc"),
    TITLE_ASCENDING("title.asc"),
    TITLE_DESCENDING("title.desc")
}