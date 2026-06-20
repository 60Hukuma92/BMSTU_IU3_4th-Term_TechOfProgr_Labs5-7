package com.test.magicalhaven.di

import android.content.Context
import com.test.magicalhaven.data.repository.CreatureRepository
import com.test.magicalhaven.data.repository.CreatureRepositoryImpl
import com.test.magicalhaven.data.repository.RemoteCreatureRepositoryImpl
import com.test.magicalhaven.data.remote.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context { 
        return context
    }

    @Provides
    @Singleton
    fun provideCsvFileName(): String {
        return "creatures.csv"
    }

    @Provides
    @Singleton
    fun provideCreatureRepository(
        context: Context,
        apiService: CreatureApiService,
        playerApiService: PlayerApiService,
        authApiService: AuthApiService,
        authInterceptor: AuthInterceptor
    ): CreatureRepository {
        val useRemote = true 
        return if (useRemote) {
            RemoteCreatureRepositoryImpl(apiService, playerApiService, authApiService, authInterceptor)
        } else {
            CreatureRepositoryImpl(context)
        }
    }
}
