package com.example.mymovielibrary.di

import android.content.Context
import com.example.mymovielibrary.data.local.LocalInfoManagerImpl
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideLocalInfoManager(@ApplicationContext context: Context): LocalInfoManagerImpl {
        return LocalInfoManagerImpl(context)
    }

    @Provides
    fun userPrefsWriter(localStore: LocalInfoManagerImpl): LocalStoreWriter {
        return localStore
    }

    @Provides
    fun userPrefsReader(localStore: LocalInfoManagerImpl): LocalStoreReader {
        return localStore
    }
}