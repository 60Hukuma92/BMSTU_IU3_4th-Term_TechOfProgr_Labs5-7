package com.test.magicalhaven.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.bmstu.magicshelter.data.repository.CreatureRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCsvFileName(): String {
        return "creatures.csv"
    }

    @Provides
    @Singleton
    fun provideCreatureRepository(
        @ApplicationContext context: Context,
        csvFileName: String
    ): CreatureRepository {
        return CreatureRepository(context, csvFileName)
    }
}