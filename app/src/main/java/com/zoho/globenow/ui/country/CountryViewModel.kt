package com.zoho.globenow.ui.country

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.model.LocationModel
import com.zoho.globenow.data.model.WeatherCondition
import com.zoho.globenow.data.model.weather.Weather
import com.zoho.globenow.data.repo.CountryRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CountryViewModel @ViewModelInject constructor(private val countryRepo: CountryRepo) :
    ViewModel() {

    val countries: LiveData<List<CountryEntity>> = countryRepo.getCountries()
    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
    get() {
        return _currentWeather
    }


    init {
        fetchCountries()
    }

    fun fetchCurrentWeather(locationModel: LocationModel) {
        viewModelScope.launch {
            countryRepo.fetchWeatherReport(locationModel).collect {
                _currentWeather.value = it
            }
        }
    }

    fun getWeatherIcon(weather: Weather): Int {
        if (!weather.weather.isNullOrEmpty()) {
            return when(weather.weather.first().main) {
                WeatherCondition.Thunderstorm.name -> R.drawable.ic_thunder_storm
                WeatherCondition.Clear.name -> R.drawable.ic_clear
                WeatherCondition.Clouds.name -> R.drawable.ic_cloud
                WeatherCondition.Drizzle.name -> R.drawable.ic_drizzle
                WeatherCondition.Others.name -> R.drawable.ic_weather
                WeatherCondition.Rain.name -> R.drawable.ic_rain
                WeatherCondition.Snow.name -> R.drawable.ic_snow
                else -> R.drawable.ic_weather
            }
        }
        return R.drawable.ic_weather
    }
    private fun fetchCountries() {
        viewModelScope.launch {
            countryRepo.fetchCountries()
        }
    }
}