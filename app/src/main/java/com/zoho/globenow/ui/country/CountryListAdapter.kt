package com.zoho.globenow.ui.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity

/**
 * Created by dcrew on 16/02/18.
 */

class CountryListAdapter(private val countries: List<CountryEntity>) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_country_list, parent, false)
        return MyViewHolder(rootView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country = countries[position]

        holder.tvCountryName.text = country.name
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class MyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        var tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        var ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)

        init {
            // TODO Listioner
        }

    }
}
