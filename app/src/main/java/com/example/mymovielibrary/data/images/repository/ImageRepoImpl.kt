package com.example.mymovielibrary.data.images.repository

import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toIcon
import com.example.mymovielibrary.data.images.api.ImageApi
import com.example.mymovielibrary.domain.images.model.ImageSize
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class ImageRepoImpl(private val api: ImageApi): ImageRepository {

    override suspend fun getIcon(sizeType: ImageSize, imagePath: String): Result<ImageBitmap, DataError.Network> {
        return try {
            val responseInBytes = api.loadPhoto(sizeType.size, imagePath).bytes()
            val bitmap = BitmapFactory.decodeByteArray(responseInBytes, 0, responseInBytes.size)

            Result.Success(bitmap.asImageBitmap())
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting image"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting image"))
        }
    }
}