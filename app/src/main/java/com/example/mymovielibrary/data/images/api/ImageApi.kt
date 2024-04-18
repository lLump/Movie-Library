package com.example.mymovielibrary.data.images.api

import android.graphics.Bitmap
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {
    @GET("{size}/{path}")
    suspend fun loadPhoto(@Path("size") size: String, @Path("path") path: String): ResponseBody
}