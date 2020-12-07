package com.zoho.globenow.data.repo

import androidx.lifecycle.LiveData
import com.zoho.globenow.data.local.GlobeDB
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.model.LocationModel
import com.zoho.globenow.data.model.country.Country
import com.zoho.globenow.data.model.weather.Weather
import com.zoho.globenow.data.remote.RemoteClient
import com.zoho.globenow.util.api.ApiHandler
import com.zoho.globenow.util.api.CallAwait
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryRepo @Inject constructor(db: GlobeDB): ApiHandler() {
    private val countryDao = db.countryDao()

    fun getCountries(): LiveData<List<CountryEntity>> {
        return countryDao.countries()
    }

    suspend fun fetchCountries() {
        val response: Result<List<Country>?> = CallAwait().runCatching {
            safeApiCall {
                RemoteClient.countryWebService.countries()
            }
        }
        response.getOrNull()?.let { countries ->
            val countryEntity = countries.map {
                it.toCountryEntity()
            }
            countryDao.insertCountries(countryEntity)
        }
    }

    fun fetchWeatherReport(location: LocationModel) = flow {
        val response: Result<Weather?> = CallAwait().runCatching {
            safeApiCall {
                RemoteClient.weatherWebService.currentWeather(
                    lat = location.latitude,
                    long = location.longitude
                )
            }
        }
        emit(response.getOrNull())
    }
}