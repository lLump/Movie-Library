package com.example.mymovielibrary.data.account.usecase

import com.example.mymovielibrary.data.storage.ApiData
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class AccountIdGetter(private val api: AccountApi) : GetAccountId {

    override suspend operator fun invoke(sessionId: String): Result<Int, DataError.Network> {
        return try {
            val response = api.getAccountDetails(ApiData.APIKEY, sessionId)

            Result.Success(response.id)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting accountId"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network(e.message ?: "Failed getting accountId"))
        }
    }

}