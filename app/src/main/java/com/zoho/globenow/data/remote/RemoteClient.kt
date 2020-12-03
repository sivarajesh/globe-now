package com.zoho.globenow.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


internal object RemoteClient {
    val client: Retrofit
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
            return Retrofit.Builder()
                    .baseUrl("https://restcountries.eu/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }

    val countryWebService: CountryWebService by lazy {
        return@lazy client.create(CountryWebService::class.java)
    }
}
