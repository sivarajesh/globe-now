package com.zoho.globenow.data.model


import com.google.gson.annotations.SerializedName
import com.zoho.globenow.data.local.entity.CountryEntity

data class Country(
    @SerializedName("alpha2Code")
    val alpha2Code: String = "",
    @SerializedName("alpha3Code")
    val alpha3Code: String = "",
    @SerializedName("altSpellings")
    val altSpellings: List<String> = listOf(),
    @SerializedName("area")
    val area: Double = 0.0,
    @SerializedName("borders")
    val borders: List<String> = listOf(),
    @SerializedName("callingCodes")
    val callingCodes: List<String> = listOf(),
    @SerializedName("capital")
    val capital: String = "",
    @SerializedName("cioc")
    val cioc: String = "",
    @SerializedName("currencies")
    val currencies: List<Currency> = listOf(),
    @SerializedName("demonym")
    val demonym: String = "",
    @SerializedName("flag")
    val flag: String = "",
    @SerializedName("gini")
    val gini: Double = 0.0,
    @SerializedName("languages")
    val languages: List<Language> = listOf(),
    @SerializedName("latlng")
    val latlng: List<Double> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("nativeName")
    val nativeName: String = "",
    @SerializedName("numericCode")
    val numericCode: String = "",
    @SerializedName("population")
    val population: Int = 0,
    @SerializedName("region")
    val region: String = "",
    @SerializedName("regionalBlocs")
    val regionalBlocs: List<RegionalBloc> = listOf(),
    @SerializedName("subregion")
    val subregion: String = "",
    @SerializedName("timezones")
    val timezones: List<String> = listOf(),
    @SerializedName("topLevelDomain")
    val topLevelDomain: List<String> = listOf(),
    @SerializedName("translations")
    val translations: Translations = Translations()
) {
    fun toCountryEntity(): CountryEntity {
        return CountryEntity(0, name, area, population, flag)
    }
}