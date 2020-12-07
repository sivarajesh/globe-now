package com.zoho.globenow.ui.countrydetail

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.util.svg.GlideApp
import com.zoho.globenow.util.svg.GlideUtil
import com.zoho.globenow.util.svg.SvgSoftwareLayerSetter


/**
 * Created by dcrew on 16/02/18.
 */

class CountryDetailsAdapter(private val context: Context, private val details: List<Pair<String, String>>) : RecyclerView.Adapter<CountryDetailsAdapter.MyViewHolder>() {

    private var requestBuilder: RequestBuilder<PictureDrawable> = GlideApp.with(context)
        .`as`(PictureDrawable::class.java)
        .placeholder(android.R.drawable.ic_menu_upload)
        .error(android.R.drawable.alert_dark_frame)
        .transition(DrawableTransitionOptions.withCrossFade())
        .listener(SvgSoftwareLayerSetter())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_country_detail,
            parent,
            false
        )
        return MyViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pair = details[position]
        holder.tvLabel.text = pair.first
        holder.tvValue.text = pair.second

        Glide.with(context).load(R.drawable.ic_thunder_storm).into(holder.ivIcon)
    }

    override fun getItemCount(): Int {
        return details.size
    }

    inner class MyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        var tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        var tvValue: TextView = itemView.findViewById(R.id.tvValue)
        var ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    }

}
