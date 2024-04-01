package com.example.mymovielibrary.data.account.repository.cases

import com.example.mymovielibrary.data.ApiData
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.domain.account.repository.AccountIdGetter
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result
import retrofit2.HttpException
import javax.inject.Inject

class AccountIdGetterImpl @Inject constructor(
    private val api: AccountApi
) : AccountIdGetter {

    override suspend fun getAccountId(sessionId: String): Result<Int, DataError.Network> {
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