package com.zoho.globenow

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobeApplication: Application() {
    var isLocationPermissionDeniedForThisSession = false

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        if (!BuildConfig.DEBUG)
            MultiDex.install(this)
    }
}