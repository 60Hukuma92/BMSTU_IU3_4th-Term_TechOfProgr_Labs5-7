package com.test.magicalhaven.di

import com.test.magicalhaven.data.remote.CreatureApiService
import com.test.magicalhaven.data.remote.StatusApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Special IP for Android Emulator to access localhost
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCreatureApiService(retrofit: Retrofit): CreatureApiService {
        return retrofit.create(CreatureApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStatusApiService(retrofit: Retrofit): StatusApiService {
        return retrofit.create(StatusApiService::class.java)
    }
}
