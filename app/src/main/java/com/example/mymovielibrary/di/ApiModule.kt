package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.lists.api.CollectionApi
import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.api.MediaManagerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

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
    fun listApi(retrofit: Retrofit): ListApi {
        return retrofit.create(ListApi::class.java)
    }

    @Provides
    fun collectionApi(retrofit: Retrofit): CollectionApi {
        return retrofit.create(CollectionApi::class.java)
    }

    @Provides
    fun mediaManagerApi(retrofit: Retrofit): MediaManagerApi {
        return retrofit.create(MediaManagerApi::class.java)
    }

}