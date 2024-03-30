package com.example.mymovielibrary.core.di

import android.content.Context
import com.example.mymovielibrary.auth.data.api.AuthApi
import com.example.mymovielibrary.auth.data.repository.AuthHelperImpl
import com.example.mymovielibrary.auth.data.repository.AuthRepoImpl
import com.example.mymovielibrary.auth.domain.repository.AuthRepository
import com.example.mymovielibrary.auth.data.repository.UserStoreImpl
import com.example.mymovielibrary.auth.domain.repository.AuthHelper
import com.example.mymovielibrary.auth.domain.repository.UserStore
import com.example.mymovielibrary.core.data.ApiData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Retention(AnnotationRetention.BINARY)
annotation class RequireAuth

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAuthRepo(): AuthRepository {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/authentication/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor {
                        val request = it.request().newBuilder()
                            .addHeader("accept", "application/json")
                            .addHeader("Authorization", "Bearer ${ApiData.bearer}")
                            .build()
                        it.proceed(request)
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authApi = retrofit.create(AuthApi::class.java)
        return AuthRepoImpl(api = authApi)
    }

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): UserStore {
        return UserStoreImpl(context)
    }
}