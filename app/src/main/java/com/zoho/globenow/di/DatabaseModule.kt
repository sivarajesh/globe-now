package com.zoho.globenow.di

import android.content.Context
import androidx.room.Room
import com.zoho.globenow.data.local.GlobeDB
import com.zoho.globenow.data.local.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): GlobeDB {
        return Room.databaseBuilder(
            appContext,
            GlobeDB::class.java, GlobeDB.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCountryDao(database: GlobeDB): CountryDao {
        return database.countryDao()
    }
}