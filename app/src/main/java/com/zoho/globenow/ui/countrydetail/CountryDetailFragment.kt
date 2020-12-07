package com.zoho.globenow.ui.countrydetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.data.model.LocationModel
import com.zoho.globenow.databinding.CountryDetailFragmentBinding
import com.zoho.globenow.databinding.WeatherLayoutBinding
import com.zoho.globenow.util.svg.GlideUtil
import kotlin.math.roundToInt

class CountryDetailFragment : Fragment() {

    private lateinit var details: ArrayList<Pair<String, String>>
    private val viewModel: CountryDetailViewModel by activityViewModels()
    private var countryEntity: CountryEntity? = null

    private lateinit var binding: CountryDetailFragmentBinding
    private lateinit var weatherBinding: WeatherLayoutBinding

    companion object {
        fun newInstance(countryEntity: CountryEntity): CountryDetailFragment {
            val countryDetailFragment = CountryDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("country", countryEntity)
            countryDetailFragment.arguments = bundle
            return countryDetailFragment
        }

        const val TAG = "CountryDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountryDetailFragmentBinding.inflate(inflater, container, false)
        weatherBinding = binding.weatherLayout2

        countryEntity = arguments?.getParcelable("country")

        if (countryEntity == null) {
            countryEntity = savedInstanceState?.getParcelable("country")
        }

        countryEntity?.let { countryEntity ->
            Log.d(TAG, "onCreateView: Country - $countryEntity")

            details = arrayListOf()
            details.addAll(viewModel.getDetailsData(countryEntity))
            val adapter = CountryDetailsAdapter(requireContext(), details)
            binding.rvCountryDetail.adapter = adapter

            weatherBinding.tvLocation.text = countryEntity.name
            GlideUtil.glideBuilder(requireContext()).load(Uri.parse(countryEntity.flag))
                .into(weatherBinding.ivFlag)

            if (countryEntity.lat != null && countryEntity.lng != null) {
                viewModel.currentWeather(LocationModel(countryEntity.lat, countryEntity.lng))
                    .observe(viewLifecycleOwner, { currentWeather ->
                        currentWeather?.let { weather ->
                            weatherBinding.tvCountryName.text = weather.name
                            if (weather.weather.isNotEmpty()) {
                                weatherBinding.tvWeatherStatus.text = weather.weather.first().main
                            }
                            val temperature = "${weather.main.temp.roundToInt()}\u00B0"
                            weatherBinding.tvTemp.text = temperature
                            Glide.with(requireActivity())
                                .load(viewModel.getWeatherIcon(weather))
                                .into(weatherBinding.ivWeather)
                        }
                    })
            }
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("country", countryEntity)
        super.onSaveInstanceState(outState)
    }
}