package com.example.mymovielibrary.domain.lists.helper

import com.example.mymovielibrary.domain.lists.model.UserCollection

interface ListHelper {

    suspend fun getUserCollections(): List<UserCollection>

}