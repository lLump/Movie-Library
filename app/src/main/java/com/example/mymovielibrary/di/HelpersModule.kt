package com.example.mymovielibrary.di

import com.example.mymovielibrary.domain.account.repository.AccountRepository
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.account.helper.AccountHelper
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.LocalUserInfo
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.data.auth.helper.AuthHelperImpl
import com.example.mymovielibrary.data.account.helper.AccountHelperImpl
import com.example.mymovielibrary.data.lists.helper.ListHelperImpl
import com.example.mymovielibrary.domain.lists.helper.ListHelper
import com.example.mymovielibrary.domain.lists.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
class HelpersModule {

    @Provides
    fun authHelper(
        scope: CoroutineScope,
        authRepo: AuthRepository,
        userCreds: LocalUserInfo,
        accountId: GetAccountId
    ): AuthHelper {
        return AuthHelperImpl(scope, authRepo, userCreds, accountId)
    }

    @Provides
    fun profileHelper(
        profileRepo: AccountRepository,
        imageRepo: ImageRepository
    ): AccountHelper {
        return AccountHelperImpl(profileRepo, imageRepo)
    }

    @Provides
    fun listHelper(
        scope: CoroutineScope,
        listRepo: ListRepository
    ): ListHelper {
        return ListHelperImpl(scope, listRepo)
    }

}