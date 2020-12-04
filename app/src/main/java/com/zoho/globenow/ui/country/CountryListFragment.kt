package com.zoho.globenow.ui.country

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity

class CountryListFragment : Fragment() {

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private val viewModel: CountryViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.country_list_fragment, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val rvCountryList = view.findViewById<RecyclerView>(R.id.rvCountryList)
        val tvNoData = view.findViewById<TextView>(R.id.tvNoData)

        val countries = arrayListOf<CountryEntity>()
        val adapter = CountryListAdapter(countries)
        rvCountryList.adapter = adapter

        viewModel.countries.observe(viewLifecycleOwner, {
            countries.clear()
            countries.addAll(it)
            adapter.notifyDataSetChanged()
        })

        return view
    }
}