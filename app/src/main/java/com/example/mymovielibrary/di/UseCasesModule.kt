package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.use_cases.CollectionCreatorImpl
import com.example.mymovielibrary.domain.lists.repository.CollectionRepo
import com.example.mymovielibrary.domain.use_cases.CollectionCreatorInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    fun collectionCreator(repo: CollectionRepo): CollectionCreatorInterface {
        return CollectionCreatorImpl(repo)
    }
}