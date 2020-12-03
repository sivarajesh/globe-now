package com.zoho.globenow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zoho.globenow.ui.country.CountryListFragment

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