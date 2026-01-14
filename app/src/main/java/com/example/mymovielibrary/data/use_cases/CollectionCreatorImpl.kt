package com.example.mymovielibrary.data.use_cases

import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result
import com.example.mymovielibrary.domain.use_cases.CollectionCreator
import javax.inject.Inject

class CollectionCreatorImpl @Inject constructor(
    private val repo: CollectionRepo
): CollectionCreator {
    override suspend fun invoke(name: String, description: String, isPublic: Boolean): Result<Boolean, DataError> {
        return repo.createCollection(name, description, isPublic)
    }
}