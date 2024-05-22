package com.example.mymovielibrary.di

import com.example.mymovielibrary.data.lists.adapter.ResultsAdapterFactory
import com.example.mymovielibrary.data.lists.model.MediaItemResponse
import com.example.mymovielibrary.data.lists.model.MovieResponse
import com.example.mymovielibrary.data.lists.model.TVShowResponse
import com.example.mymovielibrary.data.storage.ApiData
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(ResultsAdapterFactory())
                    .add(
                        PolymorphicJsonAdapterFactory.of(
                            MediaItemResponse::class.java, "media_type"
                        )
                            .withSubtype(MovieResponse::class.java, "movie")
                            .withSubtype(TVShowResponse::class.java, "tv")
                    )
                    .build()
            )
        )
        .build()

}