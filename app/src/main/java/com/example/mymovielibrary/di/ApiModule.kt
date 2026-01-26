package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.remote.account.api.AccountApi
import com.example.mymovielibrary.data.remote.auth.api.AuthApi
import com.example.mymovielibrary.data.remote.lists.api.CollectionManagerApi
import com.example.mymovielibrary.data.remote.lists.api.UserListsApi
import com.example.mymovielibrary.data.remote.lists.api.MediaManagerApi
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
    fun listApi(retrofit: Retrofit): UserListsApi {
        return retrofit.create(UserListsApi::class.java)
    }

    @Provides
    fun collectionApi(retrofit: Retrofit): CollectionManagerApi {
        return retrofit.create(CollectionManagerApi::class.java)
    }

    @Provides
    fun mediaManagerApi(retrofit: Retrofit): MediaManagerApi {
        return retrofit.create(MediaManagerApi::class.java)
    }

}