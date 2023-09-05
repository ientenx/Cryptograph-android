package com.example.cryptograph.di

import android.content.Context
import android.util.Log
import com.example.cryptograph.App
import com.example.cryptograph.network.AuthService
import com.example.cryptograph.network.AuthenticationInterceptor
import com.example.cryptograph.network.StudentService
import com.example.cryptograph.session.UserSessionManager
import com.example.network.EncryptedOkHttpClient
import com.example.network.NetworkCryptography
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://dl1.rustbridge.site:6541/"

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context,userSessionManager: UserSessionManager): Retrofit {
        val coroutineScope = (context as App).coroutineScope
        val keySet = userSessionManager.keySet

        val logging = HttpLoggingInterceptor { log ->
            Log.i("network", log)
            println(log)
        }

        val okHttpClient = EncryptedOkHttpClient(NetworkCryptography(coroutineScope,keySet)).builder()
            .addInterceptor(logging)
            .addInterceptor(AuthenticationInterceptor(userSessionManager))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStudentService(retrofit: Retrofit): StudentService = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create()
}