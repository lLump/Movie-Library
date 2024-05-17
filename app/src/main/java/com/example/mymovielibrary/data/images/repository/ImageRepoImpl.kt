package com.example.mymovielibrary.data.images.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.mymovielibrary.data.storage.TAG
import com.example.mymovielibrary.data.images.api.ImageApi
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

class ImageRepoImpl(private val api: ImageApi): ImageRepository {

    override suspend fun getIcon(sizeType: ImageSize, imagePath: String): Result<Bitmap, DataError.Network> {
        return try {
            val responseInBytes = api.loadPhoto(sizeType.size, imagePath).bytes()
            val bitmap = BitmapFactory.decodeByteArray(responseInBytes, 0, responseInBytes.size)

            Result.Success(bitmap)
        } catch (e: Exception) {
            Log.e(TAG, e.stackTraceToString())
            Result.Error(DataError.Network(e.message ?: "Failed getting image"))
        }
    }
}