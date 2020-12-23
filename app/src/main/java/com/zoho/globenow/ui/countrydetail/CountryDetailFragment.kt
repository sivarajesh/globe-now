package com.zoho.globenow.ui.countrydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.databinding.CountryDetailFragmentBinding

class CountryDetailFragment : Fragment() {

    private lateinit var details: ArrayList<Pair<String, String>>
    private val viewModel: CountryDetailViewModel by activityViewModels()
    private var countryEntity: CountryEntity? = null

    private lateinit var binding: CountryDetailFragmentBinding

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        countryEntity = arguments?.getParcelable("country")
        if (countryEntity == null) {
            countryEntity = savedInstanceState?.getParcelable("country")
        }

        countryEntity?.let { countryEntity ->

            if (countryEntity.lat != null && countryEntity.lng != null) {
                viewModel.fetchCurrentWeather(countryEntity)
            }

            details = arrayListOf()
            details.addAll(viewModel.getDetailsData(countryEntity))
            val adapter = CountryDetailsAdapter(details)
            binding.rvCountryDetail.adapter = adapter
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("country", countryEntity)
        super.onSaveInstanceState(outState)
    }
}