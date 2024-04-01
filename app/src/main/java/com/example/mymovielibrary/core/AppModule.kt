package com.example.mymovielibrary.core

import android.content.Context
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.repository.cases.AccountIdGetterImpl
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.data.auth.repository.UserCredsImpl
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.data.auth.repository.AuthHelperImpl
import com.example.mymovielibrary.domain.account.repository.AccountIdGetter
import com.example.mymovielibrary.domain.auth.repository.AuthHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun authRepo(retrofit: Retrofit): AuthRepository {
        return AuthRepoImpl(api = retrofit.create(AuthApi::class.java))
    }

    @Provides
    fun accountIdGetter(@RetrofitAccountId retrofit: Retrofit): AccountIdGetter {
        return AccountIdGetterImpl(api = retrofit.create(AccountApi::class.java))
    }

    @Provides
    fun userCredentials(@ApplicationContext context: Context): UserCredentials {
        return UserCredsImpl(context)
    }

    @Provides
    fun authHelper(scope: CoroutineScope, repo: AuthRepository, userCreds: UserCredentials, accountId: AccountIdGetter): AuthHelper {
        return AuthHelperImpl(scope, repo, userCreds, accountId)
    }
}