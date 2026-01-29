package com.example.mymovielibrary.data.remote.lists.model.media

import com.example.mymovielibrary.data.remote.lists.adapter.MediaItemAdapter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllTrendingResponse(
    val page: Int,
    @Json(name = "results") @MediaItemAdapter val results: List<MediaItemResponse>,
    val total_pages: Int,
    val total_results: Int
)
