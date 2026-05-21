package com.test.magicalhaven.di

import android.content.Context
import com.test.magicalhaven.data.repository.CreatureRepository
import com.test.magicalhaven.data.repository.CreatureRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context { // Теперь Даггер знает, где брать Context
        return context
    }

    @Provides
    @Singleton
    fun provideCsvFileName(): String {
        return "creatures.csv"
    }

    @Provides
    @Singleton
    fun provideCreatureRepository(context: Context): CreatureRepository {
        return CreatureRepositoryImpl(context)
    }
}
