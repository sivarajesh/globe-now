package com.zoho.globenow.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zoho.globenow.data.local.entity.CountryEntity

@Dao
interface CountryDao {
    @Query("select * from country order by name")
    fun countries(): PagingSource<Int, CountryEntity>

    @Query("select * from country where name like :searchString||'%' or name like '% '||:searchString||'%' order by name LIKE :searchString||'%' DESC, name")
    fun countries(searchString: String): PagingSource<Int, CountryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountries(countries: List<CountryEntity>): List<Long>

    @Query("select * from country where countryCode = :code limit 1")
    suspend fun countryByCode(code: String): List<CountryEntity>
}