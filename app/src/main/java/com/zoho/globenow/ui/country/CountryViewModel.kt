package com.zoho.globenow.ui.country

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.repo.CountryRepo
import kotlinx.coroutines.launch

class CountryViewModel @ViewModelInject constructor(private val countryRepo: CountryRepo) :
    ViewModel() {

    val countries: LiveData<List<CountryEntity>>

    init {
        countries = countryRepo.getCountries()
        fetchCountries()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            countryRepo.fetchCountries()
        }
    }

}