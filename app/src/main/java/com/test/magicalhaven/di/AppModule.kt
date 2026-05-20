package com.test.magicalhaven.di

import android.content.Context
import com.test.magicalhaven.data.repository.CreatureRepository
import com.test.magicalhaven.data.repository.CreatureRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideCsvFileName(): String {
        return "creatures.csv"
    }

    @Provides
    @Singleton
    fun provideCreatureRepository(
        context: Context,
        csvFileName: String
    ): CreatureRepository {
        return CreatureRepositoryImpl(context, csvFileName)
    }
}
