package com.zoho.globenow.ui.countrydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.model.LocationModel
import com.zoho.globenow.data.model.WeatherCondition
import com.zoho.globenow.data.model.weather.Weather
import com.zoho.globenow.data.repo.CountryRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CountryDetailViewModel @ViewModelInject constructor(private val countryRepo: CountryRepo) :
    ViewModel() {

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
        get() {
            return _currentWeather
        }

    fun fetchCurrentWeather(country: CountryEntity) {
        viewModelScope.launch {
            countryRepo.fetchWeatherReport(LocationModel(country.lat!!, country.lng!!)).collect { weather ->
                weather?.let {
                    weather.mainLocation = country.name
                    weather.weatherIcon = getWeatherIcon(weather)
                    weather.subLocation = weather.name
                    weather.countryFlagUrl = country.flag
                    _currentWeather.value = weather
                }
            }
        }
    }

    private fun getWeatherIcon(weather: Weather): Int {
        if (!weather.weather.isNullOrEmpty()) {
            return when (weather.weather.first().main) {
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

    fun getDetailsData(countryEntity: CountryEntity): List<Pair<String, String>> {
        val pairs = ArrayList<Pair<String, String>>()
        pairs.add(Pair("Population", countryEntity.population.toString()))
        pairs.add(Pair("Area", countryEntity.area.toString()))
        pairs.add(Pair("Capital", countryEntity.capital))
        pairs.add(Pair("Region", countryEntity.region))
        return pairs
    }
}