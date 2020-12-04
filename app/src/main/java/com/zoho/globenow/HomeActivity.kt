package com.zoho.globenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zoho.globenow.ui.country.CountryListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CountryListFragment.newInstance())
                    .commitNow()
        }
    }
}