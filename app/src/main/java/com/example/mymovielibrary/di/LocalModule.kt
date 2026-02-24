package com.example.mymovielibrary.di

import android.content.Context
import com.example.mymovielibrary.data.local.LocalInfoManagerImpl
import com.example.mymovielibrary.data.local.SettingsManagerImpl
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.LocalStoreWriter
import com.example.mymovielibrary.domain.local.SettingsReader
import com.example.mymovielibrary.domain.local.SettingsWriter
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

    @Singleton
    @Provides
    fun provideSettingsManager(@ApplicationContext context: Context): SettingsManagerImpl {
        return SettingsManagerImpl(context)
    }

    @Provides
    fun userPrefsWriter(localStore: LocalInfoManagerImpl): LocalStoreWriter {
        return localStore
    }

    @Provides
    fun userPrefsReader(localStore: LocalInfoManagerImpl): LocalStoreReader {
        return localStore
    }

    @Provides
    fun settingsPrefsWriter(settingsPrefs: SettingsManagerImpl): SettingsWriter {
        return settingsPrefs
    }

    @Provides
    fun settingsPrefsReader(settingsPrefs: SettingsManagerImpl): SettingsReader {
        return settingsPrefs
    }
}