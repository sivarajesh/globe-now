package com.zoho.globenow.di

import android.content.Context
import com.google.android.gms.location.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@InstallIn(FragmentComponent::class)
@Module
class LocationServiceModule {

    @Provides
    fun provideFusedLocationProviderClient(@ActivityContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideLocationSettingsRequestBuilder(locationRequest: LocationRequest): LocationSettingsRequest.Builder {
        return LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    }

    @Provides
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest.create()?.apply {
            interval = 30000
            fastestInterval = 30000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            maxWaitTime = 5000
        }!!
    }

    @Provides
    fun provideSettingsClient(@ActivityContext context: Context): SettingsClient {
        return LocationServices.getSettingsClient(context)
    }
}