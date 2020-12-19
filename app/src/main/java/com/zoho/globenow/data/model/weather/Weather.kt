package com.zoho.globenow.data.model.weather


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("base")
    val base: String = "",
    @SerializedName("clouds")
    val clouds: Clouds = Clouds(),
    @SerializedName("cod")
    val cod: Int = 0,
    @SerializedName("coord")
    val coord: Coord = Coord(),
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("rain")
    val rain: Rain = Rain(),
    @SerializedName("sys")
    val sys: Sys = Sys(),
    @SerializedName("timezone")
    val timezone: Int = 0,
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("weather")
    val weather: List<WeatherX> = listOf(),
    @SerializedName("wind")
    val wind: Wind = Wind()
) {
    var weatherIcon: Int? = null
    var mainLocation: String? = null
    var subLocation: String? = null
    var countryFlagUrl: String? = null
}