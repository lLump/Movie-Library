package com.example.mymovielibrary.core

import com.example.mymovielibrary.data.ApiData
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitAccountId

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun retrofitUrlV3(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
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
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @RetrofitAccountId
    fun retrofitAccount(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .build()
                    it.proceed(request)
                }.build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}