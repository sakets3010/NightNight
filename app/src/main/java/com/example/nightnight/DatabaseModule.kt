package com.example.nightnight

import android.content.Context
import androidx.room.Room
import com.example.nightnight.db.NightDao
import com.example.nightnight.db.NightDatabase
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideNightDao(nightDatabase: NightDatabase): NightDao {
        return nightDatabase.nightDao()
    }

    @Provides
    @Singleton
    fun provideNightDatabase(@ApplicationContext appContext: Context): NightDatabase {
        return Room.databaseBuilder(
            appContext,
            NightDatabase::class.java,
            "night_database"
        ).build()
    }
}
