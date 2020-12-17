package com.zoho.globenow.ui.country

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.zoho.globenow.GlobeApplication
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.model.LocationModel
import com.zoho.globenow.data.model.weather.Weather
import com.zoho.globenow.databinding.CountryListFragmentBinding
import com.zoho.globenow.ui.countrydetail.CountryDetailFragment
import com.zoho.globenow.util.svg.GlideUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class CountryListFragment : Fragment(), CountryListAdapter.OnCountrySelectionListener {

    private var isLocationPermissionDenied: Boolean = false
    private lateinit var filteredCountries: ArrayList<CountryEntity>
    private lateinit var countries: ArrayList<CountryEntity>
    private lateinit var adapter: CountryListAdapter
    private val viewModel: CountryViewModel by activityViewModels()
    private var isGPSEnabled = false
    private var currentWeather: Weather? = null

    //    FOR LOCATION
    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var builder: LocationSettingsRequest.Builder

    @Inject
    lateinit var client: SettingsClient

    @Inject
    lateinit var locationRequest: LocationRequest
    private var requestingLocationUpdates: Boolean = false

    private lateinit var binding: CountryListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = CountryListFragmentBinding.inflate(inflater, container, false)
        updateValuesFromBundle(savedInstanceState)

        countries = arrayListOf()
        filteredCountries = arrayListOf<CountryEntity>()
        adapter = CountryListAdapter(requireContext(), filteredCountries, this)
        binding.rvCountryList.adapter = adapter

        viewModel.countries.observe(viewLifecycleOwner, {
            countries.clear()
            countries.addAll(it)
            applySearch(binding.etSearch.text.toString().trim())
            setCurrentLocation()
        })

        viewModel.currentWeather.observe(viewLifecycleOwner, {
            binding.tvLableLocation.visibility = View.VISIBLE
            binding.weatherLayout.root.visibility = View.VISIBLE
            currentWeather = it
            currentWeather?.let { weather ->
                binding.weatherLayout.tvLocation.text = weather.name
                if (weather.weather.isNotEmpty()) {
                    binding.weatherLayout.tvWeatherStatus.text = weather.weather.first().main
                }
                val temperature = "${weather.main.temp.roundToInt()}\u00B0"
                binding.weatherLayout.tvTemp.text = temperature
                Glide.with(requireActivity())
                    .load(viewModel.getWeatherIcon(weather))
                    .into(binding.weatherLayout.ivWeather)
                setCurrentLocation()
            }
        })

        binding.etSearch.addTextChangedListener {
            applySearch(it.toString())
        }

        return binding.root
    }

    private fun applySearch(searchString: String) {
        filteredCountries.clear()
        filteredCountries.addAll(countries.filter { countryEntity ->
            countryEntity.name.startsWith(searchString, true)
        })
        filteredCountries.addAll(countries.filter { countryEntity ->
            countryEntity.name.contains(" $searchString", true)
        })
        if (filteredCountries.isEmpty()) {
            binding.noDataLayout.visibility = View.VISIBLE
            binding.rvCountryList.visibility = View.GONE
        } else {
            binding.noDataLayout.visibility = View.GONE
            binding.rvCountryList.visibility = View.VISIBLE
        }
        adapter.notifyDataSetChanged()
    }

    private fun setCurrentLocation() {
        if (!countries.isNullOrEmpty()) {
            currentWeather?.let {
                val country =
                    countries.first { countryEntity -> countryEntity.countryCode == currentWeather!!.sys.country }
                binding.weatherLayout.tvCountryName.text = country.name
                GlideUtil.glideBuilder(requireContext()).load(Uri.parse(country.flag))
                    .into(binding.weatherLayout.ivFlag)
            }
        }
    }

    override fun onCountrySelected(countryEntity: CountryEntity) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, CountryDetailFragment.newInstance(countryEntity))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(CountryDetailFragment.TAG)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onResume() {
        super.onResume()
        if (!requestingLocationUpdates) startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(Companion.REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
    }

    private fun updateValuesFromBundle(savedInstanceState: Bundle?) {
        savedInstanceState ?: return

        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates = savedInstanceState.getBoolean(
                REQUESTING_LOCATION_UPDATES_KEY
            )
        }
    }

    fun checkLocationSettings() {
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            isGPSEnabled = true
            invokeLocationAction()
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        requireActivity(),
                        REQUEST_CODE_LOCATION_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.REQUEST_CODE_LOCATION_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Companion.REQUEST_CODE_LOCATION_PERMISSION) {
            if (!grantResults.contains(PackageManager.PERMISSION_DENIED))
                invokeLocationAction()
            else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showPermissionAlert(true)
                } else
                    showPermissionAlert(false)
            }
        }
    }


    private fun showPermissionAlert(isDeny: Boolean) {
        val dialog = AlertDialog.Builder(requireContext())

        if (isDeny) {
            Toast.makeText(
                requireContext(),
                getString(R.string.location_permission_alert),
                Toast.LENGTH_SHORT
            ).show()
            ((requireActivity().application) as GlobeApplication).isLocationPermissionDeniedForThisSession =
                true
        } else {
            dialog.setTitle("Location service")
                .setMessage(getString(R.string.alert_msg_location))
                .setPositiveButton("Ok") { dialogInterface, i ->
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Companion.REQUEST_CODE_LOCATION_PERMISSION
                    )
                    dialogInterface.dismiss()
                }
                .setNegativeButton("Cancel") { dialogInterface, i ->
                    ((requireActivity().application) as GlobeApplication).isLocationPermissionDeniedForThisSession =
                        true
                    dialogInterface.dismiss()
                }
            dialog.show()
        }

    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> checkLocationSettings()
            isLocationPermissionsGranted() -> if (!requestingLocationUpdates) startLocationUpdate()
            shouldShowRequestPermissionRationale() -> if (!((requireActivity().application) as GlobeApplication).isLocationPermissionDeniedForThisSession) showPermissionAlert(
                false
            )
            else -> if (!((requireActivity().application) as GlobeApplication).isLocationPermissionDeniedForThisSession) requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdate() {

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                viewModel.fetchCurrentWeather(LocationModel(it.latitude, it.longitude))
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        requestingLocationUpdates = true
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        requestingLocationUpdates = false
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult?.let {
                if (!it.locations.isNullOrEmpty()) {
                    val location = it.locations.first()
                    viewModel.fetchCurrentWeather(
                        LocationModel(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }
        }
    }

    private fun isLocationPermissionsGranted() =
        checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    companion object {
        fun newInstance() = CountryListFragment()
        const val TAG = "CountryListFragment"
        const val REQUEST_CODE_LOCATION_PERMISSION = 1000
        const val REQUEST_CODE_LOCATION_SETTINGS = 2000
        const val REQUESTING_LOCATION_UPDATES_KEY: String = "requestingLocationUpdates"
    }
}