package com.example.mymovielibrary.core

import com.example.mymovielibrary.domain.account.repository.ProfileRepository
import com.example.mymovielibrary.domain.account.repository.GetAccountId
import com.example.mymovielibrary.domain.account.helper.ProfileHelper
import com.example.mymovielibrary.domain.auth.helper.AuthHelper
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import com.example.mymovielibrary.domain.images.repository.ImageRepository
import com.example.mymovielibrary.data.auth.helper.AuthHelperImpl
import com.example.mymovielibrary.data.account.helper.ProfileHelperImpl
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
        userCreds: UserCredentials,
        accountId: GetAccountId
    ): AuthHelper {
        return AuthHelperImpl(scope, authRepo, userCreds, accountId)
    }

    @Provides
    fun profileHelper(
        profileRepo: ProfileRepository,
        imageRepo: ImageRepository
    ): ProfileHelper {
        return ProfileHelperImpl(profileRepo, imageRepo)
    }

    @Provides
    fun listHelper(
        scope: CoroutineScope,
        listRepo: ListRepository
    ): ListHelper {
        return ListHelperImpl(scope, listRepo)
    }

}