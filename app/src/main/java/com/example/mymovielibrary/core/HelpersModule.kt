package com.example.mymovielibrary.core

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.usecase.AccountIdGetter
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.account.helper.ProfileHelper
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.presentation.viewmodel.helpers.AuthHelperImpl
import com.example.mymovielibrary.presentation.viewmodel.helpers.ProfileHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class HelpersModule {

    @Provides
    fun authHelper(
        scope: CoroutineScope,
        repo: AuthRepository,
        userCreds: UserCredentials,
        accountId: GetAccountId
    ): AuthHelper {
        return AuthHelperImpl(scope, repo, userCreds, accountId)
    }

    @Provides
    fun profileHelper(scope: CoroutineScope, accConfig: AccountRepository): ProfileHelper {
        return ProfileHelperImpl(scope, accConfig)
    }

    @Provides
    fun accountIdGetter(@RetrofitAccountId retrofit: Retrofit): GetAccountId {
        return AccountIdGetter(api = retrofit.create(AccountApi::class.java))
    }
}