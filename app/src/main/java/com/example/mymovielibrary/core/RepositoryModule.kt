package com.example.mymovielibrary.core

import android.content.Context
import com.example.mymovielibrary.data.account.api.AccountApi
import com.example.mymovielibrary.data.account.repository.ProfileRepoImpl
import com.example.mymovielibrary.data.account.usecase.AccountIdGetter
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.data.auth.repository.UserCredsImpl
import com.example.mymovielibrary.data.images.api.ImageApi
import com.example.mymovielibrary.data.images.repository.ImageRepoImpl
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.domain.account.repository.ProfileRepository
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun userCredentials(@ApplicationContext context: Context): UserCredentials {
        return UserCredsImpl(context)
    }

    @Provides
    fun accountIdGetter(@AccountId api: AccountApi): GetAccountId {
        return AccountIdGetter(api)
    }

}