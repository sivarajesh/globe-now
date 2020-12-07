package com.zoho.globenow.data.remote

import com.zoho.globenow.data.model.country.Country
import retrofit2.http.GET

interface CountryWebService  {

    @GET("/rest/v2/all")
    suspend fun countries(): List<Country>
}