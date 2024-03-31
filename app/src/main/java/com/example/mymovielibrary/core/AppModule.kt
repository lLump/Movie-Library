package com.example.mymovielibrary.core

import android.content.Context
import com.example.mymovielibrary.data.auth.api.AuthApi
import com.example.mymovielibrary.data.auth.repository.AuthRepoImpl
import com.example.mymovielibrary.domain.auth.repository.AuthRepository
import com.example.mymovielibrary.data.auth.repository.UserStoreImpl
import com.example.mymovielibrary.domain.auth.repository.UserStore
import com.example.mymovielibrary.data.ApiData
import com.example.mymovielibrary.data.auth.repository.AuthHelperImpl
import com.example.mymovielibrary.domain.auth.repository.AuthHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideAuthHelper(scope: CoroutineScope, repo: AuthRepository, userStore: UserStore): AuthHelper {
        return AuthHelperImpl(scope, repo, userStore)
    }
}