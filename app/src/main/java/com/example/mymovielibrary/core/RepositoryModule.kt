package com.example.mymovielibrary.core

import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.repository.ProfileRepoImpl
import com.example.mymovielibrary.data.account.usecase.AccountIdGetter
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.data.images.api.ImageApi
import com.example.mymovielibrary.data.images.repository.ImageRepoImpl
import com.example.mymovielibrary.data.lists.api.ListApi
import com.example.mymovielibrary.data.lists.repository.ListRepoImpl
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.account.repository.ProfileRepository
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun authRepo(api: AuthApi): AuthRepository {
        return AuthRepoImpl(api)
    }

    @Provides
    fun profileRepository(api: AccountApi): ProfileRepository {
        return ProfileRepoImpl(api)
    }

    @Provides
    @Singleton
    fun imageRepo(api: ImageApi): ImageRepository {
        return ImageRepoImpl(api)
    }

    @Provides
    @Singleton
    fun listRepo(api: ListApi): ListRepository {
        return ListRepoImpl(api)
    }

    @Provides
    fun accountIdGetter(@AccountId api: AccountApi): GetAccountId {
        return AccountIdGetter(api)
    }

}