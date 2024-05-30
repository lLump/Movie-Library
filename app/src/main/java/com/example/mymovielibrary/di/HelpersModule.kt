package com.example.mymovielibrary.di

import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.LocalUserInfo
import com.example.mymovielibrary.data.auth.helper.AuthHelperImpl
import com.example.mymovielibrary.data.account.helper.AccountHelperImpl
import com.example.mymovielibrary.data.lists.helper.ListHelperImpl
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HelpersModule {

    @Provides
    fun authHelper(
        authRepo: AuthRepository,
        userCreds: LocalUserInfo,
    ): AuthHelper {
        return AuthHelperImpl(authRepo, userCreds)
    }

    @Provides
    fun profileHelper(
        profileRepo: AccountRepository,
        listRepo: ListRepository
    ): AccountHelper {
        return AccountHelperImpl(profileRepo, listRepo)
    }

    @Provides
    fun listHelper(
        listRepo: ListRepository
    ): ListHelper {
        return ListHelperImpl(listRepo)
    }

}