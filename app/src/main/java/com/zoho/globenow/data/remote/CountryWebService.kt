package com.zoho.globenow.data.remote

import com.zoho.globenow.data.model.Country
import retrofit2.http.Body
import retrofit2.http.POST

interface CountryWebService  {

    @POST("rest/v2/all")
    suspend fun countries(): List<Country>
}