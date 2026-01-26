package com.example.mymovielibrary.di

import android.content.Context
import com.example.mymovielibrary.data.local.LocalInfoManagerImpl
import com.example.mymovielibrary.data.remote.account.api.AccountApi
import com.example.mymovielibrary.data.remote.account.repository.AccountRepoImpl
import com.example.mymovielibrary.data.remote.auth.api.AuthApi
import com.example.mymovielibrary.data.remote.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.data.remote.lists.api.CollectionManagerApi
import com.example.mymovielibrary.data.remote.lists.api.UserListsApi
import com.example.mymovielibrary.data.remote.lists.api.MediaManagerApi
import com.example.mymovielibrary.data.remote.lists.repository.CollectionRepoImpl
import com.example.mymovielibrary.data.remote.lists.repository.ListsRepoImpl
import com.example.mymovielibrary.data.remote.lists.repository.MediaManagerRepoRepoImpl
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManagerRepo
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
    fun profileRepository(api: AccountApi): AccountRepo {
        return AccountRepoImpl(api)
    }

    @Provides
    fun listRepo(api: UserListsApi): ListsRepo {
        return ListsRepoImpl(api)
    }

    @Provides
    fun collectionRepo(api: CollectionManagerApi): CollectionRepo {
        return CollectionRepoImpl(api)
    }

    @Provides
    fun mediaManager(api: MediaManagerApi): MediaManagerRepo {
        return MediaManagerRepoRepoImpl(api)
    }

    @Provides
    fun userPrefs(@ApplicationContext context: Context): LocalInfoManagerImpl {
        return LocalInfoManagerImpl(context)
    }

}