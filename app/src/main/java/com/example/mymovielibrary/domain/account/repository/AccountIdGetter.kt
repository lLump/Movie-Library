package com.example.mymovielibrary.domain.account.repository

import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

interface AccountIdGetter {
    suspend fun getAccountId(sessionId: String): Result<Int, DataError.Network>
}