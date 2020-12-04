package com.zoho.globenow.data.remote

import com.zoho.globenow.data.model.Country
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CountryWebService  {

    @GET("/rest/v2/all")
    suspend fun countries(): List<Country>

    @GET("http://api.openweathermap.org/data/2.5/weather?lat=8.87&lon=78.02&appid=f8e6e66a5b9eb4a43827857893483af2")
    suspend fun currentWeather(): List<Country>
}