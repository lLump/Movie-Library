package com.example.mymovielibrary.di

import android.content.Context
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.repository.AccountRepoImpl
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.data.auth.repository.UserTmdbInfoImpl
import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.repository.CollectionRepoImpl
import com.example.mymovielibrary.data.lists.repository.ListRepoImpl
import com.example.mymovielibrary.data.lists.repository.MediaManagerRepoImpl
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.ListRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun authRepo(api: AuthApi): AuthRepoImpl {
        return AuthRepoImpl(api)
    }

    @Provides
    fun profileRepository(api: AccountApi): AccountRepoImpl {
        return AccountRepoImpl(api)
    }

    @Provides
    fun listRepo(api: ListApi): ListRepo {
        return ListRepoImpl(api)
    }

    @Provides
    fun collectionRepo(api: ListApi): CollectionRepo {
        return CollectionRepoImpl(api)
    }

    @Provides
    fun mediaManager(api: ListApi): MediaManager {
        return MediaManagerRepoImpl(api)
    }

    @Provides
    fun userTmdbInfo(@ApplicationContext context: Context): UserTmdbInfoImpl {
        return UserTmdbInfoImpl(context)
    }

}