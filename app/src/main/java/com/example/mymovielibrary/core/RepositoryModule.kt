package com.example.mymovielibrary.core

import android.content.Context
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.repository.ProfileRepoImpl
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.data.auth.repository.UserCredsImpl
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.domain.account.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun authRepo(retrofit: Retrofit): AuthRepository {
        return AuthRepoImpl(api = retrofit.create(AuthApi::class.java))
    }

    @Provides
    fun accountRepository(retrofit: Retrofit): AccountRepository {
        return ProfileRepoImpl(api = retrofit.create(AccountApi::class.java))
    }

    @Provides
    fun userCredentials(@ApplicationContext context: Context): UserCredentials {
        return UserCredsImpl(context)
    }

}