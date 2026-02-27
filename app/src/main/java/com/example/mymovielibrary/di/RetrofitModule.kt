package com.example.mymovielibrary.di

import com.example.mymovielibrary.BuildConfig
import com.example.mymovielibrary.data.remote.lists.adapter.ResultsAdapterFactory
import com.example.mymovielibrary.data.remote.lists.model.media.MediaItemResponse
import com.example.mymovielibrary.data.remote.lists.model.media.MovieResponse
import com.example.mymovielibrary.data.remote.lists.model.media.TVShowResponse
import com.example.mymovielibrary.domain.local.LocalStoreReader
import com.example.mymovielibrary.domain.local.SettingsReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun retrofitDefault(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .client(client)
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

    @Provides
    fun httpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .dispatcher(Dispatcher().apply {
                maxRequests = 15
                maxRequestsPerHost = 15
            })
            .build()
    }

    @Provides
    fun languageInterceptor(localStore: SettingsReader) = Interceptor { chain ->
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("language", localStore.language.iso639)
            .build()
        val request = original.newBuilder()
            .url(url)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.SAFE_API_KEY}")
            .build()
        return@Interceptor chain.proceed(request)
    }
}