package com.test.magicalhaven.di

import android.content.Context
import com.test.magicalhaven.data.repository.CreatureRepository
import com.test.magicalhaven.data.repository.CreatureRepositoryImpl
import com.test.magicalhaven.data.repository.RemoteCreatureRepositoryImpl
import com.test.magicalhaven.data.remote.CreatureApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context { // Context 4 Dagger
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
        apiService: CreatureApiService
    ): CreatureRepository {
        //switch between Local and Remote for the demo
        val useRemote = true 
        return if (useRemote) {
            RemoteCreatureRepositoryImpl(apiService)
        } else {
            CreatureRepositoryImpl(context)
        }
    }
}
