package com.zoho.globenow.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoho.globenow.data.local.entity.CountryEntity

@Dao
interface CountryDao {
    @Query("select * from country")
    fun countries(): LiveData<List<CountryEntity>>

    @Query("select * from country where name like :searchString||'%' or name like '% '||:searchString")
    fun countries(searchString: String): LiveData<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>): List<Long>
}