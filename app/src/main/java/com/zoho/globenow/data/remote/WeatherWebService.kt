package com.zoho.globenow.data.remote

import com.zoho.globenow.BuildConfig
import com.zoho.globenow.data.model.weather.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWebService  {

    @GET(BuildConfig.WEATHER_API_URL)
    suspend fun currentWeather(@Query("appid") appId: String = BuildConfig.WEATHER_API_KEY,
                               @Query("lat") lat: Double,
                               @Query("lon") long: Double,
                               @Query("units") units: String = "Metric"): Weather
}