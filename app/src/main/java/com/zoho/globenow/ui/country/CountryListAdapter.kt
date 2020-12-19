package com.zoho.globenow.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.databinding.RvCountryListBinding


/**
 * Created by dcrew on 16/02/18.
 */

class CountryListAdapter(
    private val countries: List<CountryEntity>,
    val callback: OnCountrySelectionListener
) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class MyViewHolder(val binding: RvCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.callBack = callback
        }

        fun bind(countryEntity: CountryEntity) {
            binding.country = countryEntity
            binding.executePendingBindings()
        }
    }

    interface OnCountrySelectionListener {
        fun onCountrySelected(countryEntity: CountryEntity)
    }
}
