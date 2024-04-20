package com.example.mymovielibrary.core

import android.content.Context
import com.example.mymovielibrary.data.auth.repository.UserCredsImpl
import com.example.mymovielibrary.domain.auth.repository.UserCredentials
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun applicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Provides
    fun userCredentials(@ApplicationContext context: Context): UserCredentials {
        return UserCredsImpl(context)
    }

}