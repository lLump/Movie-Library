package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.storage.ApiData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun retrofitDefault(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer ${ApiData.BEARER}")
                        .build()
                    it.proceed(request)
                }.build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @AccountId
    fun retrofitAccountId(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .build()
                    it.proceed(request)
                }.build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @RetrofitImage
    fun retrofitImage(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://image.tmdb.org/t/p/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}