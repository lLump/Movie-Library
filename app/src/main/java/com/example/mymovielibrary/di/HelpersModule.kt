package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.account.helper.AccountHelperImpl
import com.example.mymovielibrary.data.account.repository.AccountRepoImpl
import com.example.mymovielibrary.data.auth.helper.AuthHelperImpl
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.data.auth.repository.UserTmdbInfoImpl
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.lists.repository.ListsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HelpersModule {

    @Provides
    fun authHelper(
        authRepo: AuthRepoImpl,
        userCreds: UserTmdbInfoImpl,
    ): AuthHelper {
        return AuthHelperImpl(authRepo, userCreds)
    }

    @Provides
    fun profileHelper(
        profileRepo: AccountRepoImpl,
        listsRepo: ListsRepo
    ): AccountHelper {
        return AccountHelperImpl(profileRepo, listsRepo)
    }

}