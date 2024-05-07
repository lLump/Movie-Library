package com.example.mymovielibrary.domain.images.repository

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

interface ImageRepository {
    suspend fun getIcon(sizeType: ImageSize, imagePath: String): Result<Bitmap, DataError.Network>
}