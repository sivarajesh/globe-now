package com.zoho.globenow.data.repo

import androidx.lifecycle.LiveData
import com.zoho.globenow.data.local.GlobeDB
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.model.Country
import com.zoho.globenow.data.remote.RemoteClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountryRepo @Inject constructor(db: GlobeDB) {
    private val countryDao = db.countryDao()

    fun getCountries(): LiveData<List<CountryEntity>> {
        return countryDao.countries()
    }

    suspend fun fetchCountries(){
        val countries = RemoteClient.countryWebService.countries()
        val countryEntity = countries.map {
            it.toCountryEntity()
        }
        countryDao.insertCountries(countryEntity)
    }
}