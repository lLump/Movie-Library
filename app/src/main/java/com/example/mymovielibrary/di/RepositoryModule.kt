package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.remote.account.api.AccountApi
import com.example.mymovielibrary.data.remote.account.repository.AccountRepoImpl
import com.example.mymovielibrary.data.remote.auth.api.AuthApi
import com.example.mymovielibrary.data.remote.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.data.remote.lists.api.CollectionManagerApi
import com.example.mymovielibrary.data.remote.lists.api.HomeListsApi
import com.example.mymovielibrary.data.remote.lists.api.UserListsApi
import com.example.mymovielibrary.data.remote.lists.api.MediaManagerApi
import com.example.mymovielibrary.data.remote.lists.repository.CollectionRepoImpl
import com.example.mymovielibrary.data.remote.lists.repository.HomeListsRepoImpl
import com.example.mymovielibrary.data.remote.lists.repository.UserListsRepoImpl
import com.example.mymovielibrary.data.remote.lists.repository.MediaManagerRepoImpl
import com.example.mymovielibrary.domain.account.repository.AccountRepo
import com.example.mymovielibrary.domain.account.repository.AuthRepo
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.lists.repository.HomeListsRepo
import com.example.mymovielibrary.domain.lists.repository.UserListsRepo
import com.example.mymovielibrary.domain.lists.repository.MediaManagerRepo
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.SettingsReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun authRepo(api: AuthApi, localStore: LocalStoreReader): AuthRepo {
        return AuthRepoImpl(api, localStore)
    }

    @Provides
    fun profileRepository(api: AccountApi, localStore: LocalStoreReader): AccountRepo {
        return AccountRepoImpl(api, localStore)
    }

    @Provides
    fun userListsRepo(api: UserListsApi, localStore: LocalStoreReader): UserListsRepo {
        return UserListsRepoImpl(api, localStore)
    }

    @Provides
    fun homeListsRepo(api: HomeListsApi, localStore: LocalStoreReader): HomeListsRepo {
        return HomeListsRepoImpl(api, localStore)
    }

    @Provides
    fun collectionRepo(api: CollectionManagerApi, settings: SettingsReader, localStore: LocalStoreReader): CollectionRepo {
        return CollectionRepoImpl(api, settings, localStore)
    }

    @Provides
    fun mediaManager(api: MediaManagerApi, localStore: LocalStoreReader): MediaManagerRepo {
        return MediaManagerRepoImpl(api, localStore)
    }

}