package com.zoho.globenow.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.databinding.RvCountryListBinding


/**
 * Created by dcrew on 16/02/18.
 */

class CountryListAdapter(val callback: OnCountrySelectionListener) :
    PagingDataAdapter<CountryEntity, CountryListAdapter.MyViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<CountryEntity>() {
            override fun areItemsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(
                oldItem: CountryEntity,
                newItem: CountryEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    inner class MyViewHolder(val binding: RvCountryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.callBack = callback
        }

        fun bind(countryEntity: CountryEntity?) {
            binding.country = countryEntity
            binding.executePendingBindings()
        }
    }

    interface OnCountrySelectionListener {
        fun onCountrySelected(countryEntity: CountryEntity)
    }
}
