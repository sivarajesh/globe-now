package com.zoho.globenow.ui.countrydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zoho.globenow.databinding.RvCountryDetailBinding


/**
 * Created by dcrew on 16/02/18.
 */

class CountryDetailsAdapter(private val details: List<Pair<String, String>>) : RecyclerView.Adapter<CountryDetailsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvCountryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pair = details[position]
        holder.bind(pair)
    }

    override fun getItemCount(): Int {
        return details.size
    }

    inner class MyViewHolder(val binding: RvCountryDetailBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Pair<String, String>) {
            binding.data = data
            binding.executePendingBindings()
        }
    }

}
