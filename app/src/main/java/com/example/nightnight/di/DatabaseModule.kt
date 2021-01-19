package com.example.nightnight.di

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.nightnight.NightRepository
import com.example.nightnight.db.NightDao
import com.example.nightnight.db.NightDatabase
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@dagger.Module
object DBModule {

    @dagger.Provides
    fun provideNightDao(@ApplicationContext appContext: Context): NightDao =
        NightDatabase.getInstance(appContext).nightDao()

    @dagger.Provides
    fun provideNightRepository(nightDao: NightDao) = NightRepository(nightDao)
}
