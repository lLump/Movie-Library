package com.example.mymovielibrary.core

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.images.api.ImageApi
import com.example.mymovielibrary.data.lists.api.ListsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun authApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun accountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Provides
    @AccountId //Different setting of retrofit to get user ID
    fun accountIdApi(@AccountId retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun imageApi(@RetrofitImage retrofit: Retrofit): ImageApi {
        return retrofit.create(ImageApi::class.java)
    }

    @Provides
    fun listsApi(retrofit: Retrofit): ListsApi {
        return retrofit.create(ListsApi::class.java)
    }

}