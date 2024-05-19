package com.example.mymovielibrary.data.lists.helper

import com.example.mymovielibrary.domain.base.helper.BaseHelper
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.lists.model.UserCollection
import com.example.mymovielibrary.domain.lists.repository.ListRepository

class ListHelperImpl(
    private val listRepo: ListRepository
): ListHelper, BaseHelper() {

    override suspend fun getUserCollections(): List<UserCollection> {
        return request { listRepo.getUserCollections() } ?: return emptyList()
    }

}