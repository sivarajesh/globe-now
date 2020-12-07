package com.zoho.globenow.ui.country

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.zoho.globenow.R
import com.zoho.globenow.data.local.entity.CountryEntity
import com.zoho.globenow.util.svg.GlideApp
import com.zoho.globenow.util.svg.SvgSoftwareLayerSetter


/**
 * Created by dcrew on 16/02/18.
 */

class CountryListAdapter(private val context: Context, private val countries: List<CountryEntity>, val callback: OnCountrySelectionListener) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {

    private var requestBuilder: RequestBuilder<PictureDrawable> = GlideApp.with(context)
        .`as`(PictureDrawable::class.java)
        .error(android.R.drawable.alert_dark_frame)
        .transition(withCrossFade())
        .listener(SvgSoftwareLayerSetter())



//    var requestBuilder: GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> =
//        Glide.with(context)
//            .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
//                .from(Uri.class)
//                    .as(SVG.class)
//                        .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
//                            .sourceEncoder(new StreamEncoder())
//                            .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
//                            .decoder(new SvgDecoder())
//                            .listener(new SvgSoftwareLayerSetter<Uri>());

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_country_list,
            parent,
            false
        )
        return MyViewHolder(rootView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country = countries[position]

        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(android.R.drawable.ic_menu_upload)
            .transform(CenterInside())

        holder.tvCountryName.text = country.name
        val uri: Uri = Uri.parse(country.flag)
        requestBuilder.load(uri).apply(options).into(holder.ivFlag)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class MyViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        var tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        var ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)

        init {
            itemView.setOnClickListener {
                callback.onCountrySelected(countries[adapterPosition])
            }
        }

    }

    interface OnCountrySelectionListener{
        fun onCountrySelected(countryEntity: CountryEntity)
    }
}
