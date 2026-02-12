package com.example.mymovielibrary.data.remote.lists.util

import com.example.mymovielibrary.data.remote.lists.model.media.AllTrendingResponse
import com.example.mymovielibrary.data.remote.lists.model.media.MovieResponse
import com.example.mymovielibrary.data.remote.lists.model.media.TVShowResponse
import com.example.mymovielibrary.domain.lists.model.MediaItem

fun AllTrendingResponse.toMedias() : List<MediaItem> {
    return results.map {
        when (it) {
            is MovieResponse -> it.toMediaItem()
            is TVShowResponse -> it.toMediaItem()
            else -> throw Exception("People found in AllTrending")
        }
    }
}