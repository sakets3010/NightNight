package com.example.nightnight.di

import android.content.Context
import com.example.nightnight.NightRepository
import com.example.nightnight.db.NightDao
import com.example.nightnight.db.NightDatabase
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@dagger.Module
object DBModule {

    @dagger.Provides
    fun provideNightDao(@ApplicationContext appContext: Context): NightDao =
        NightDatabase.getInstance(appContext).nightDao()

    @dagger.Provides
    fun provideNightRepository(nightDao: NightDao) = NightRepository(nightDao)
}
