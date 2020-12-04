package com.zoho.globenow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zoho.globenow.data.local.dao.CountryDao
import com.zoho.globenow.data.local.entity.CountryEntity

@Database(entities = arrayOf(CountryEntity::class), version = 1)
abstract class GlobeDB : RoomDatabase() {

    companion object {
        val DATABASE_NAME = "globe_now.db"
    }
    abstract fun countryDao(): CountryDao
}
