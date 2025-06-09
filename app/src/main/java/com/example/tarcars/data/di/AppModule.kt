package com.example.tarcars.data.di

import com.example.tarcars.data.repositories.CochesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCochesRepository(): CochesRepository {
        return CochesRepository()
    }
}