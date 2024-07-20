package com.example.mymovielibrary.domain.use_cases

import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

interface CollectionCreatorInterface {
    suspend operator fun invoke(name: String, description: String, isPublic: Boolean): Result<Boolean, DataError>
}