package com.zoho.globenow.util.databinding

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zoho.globenow.util.svg.GlideApp
import com.zoho.globenow.util.svg.SvgSoftwareLayerSetter

@BindingAdapter("app:imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {

    url?.let {
        val requestBuilder: RequestBuilder<PictureDrawable> = GlideApp.with(imageView.context)
            .`as`(PictureDrawable::class.java)
            .error(android.R.drawable.alert_dark_frame)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(SvgSoftwareLayerSetter())

        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(android.R.drawable.ic_menu_upload)
            .transform(CenterInside())
        requestBuilder.load(url).apply(options).into(imageView)
    }
}

@BindingAdapter("app:imageDrawableId")
fun setImageUrl(imageView: ImageView, drawableId: Int?) {
    drawableId?.let {
                Glide.with(imageView.context)
                    .load(drawableId)
                    .into(imageView)
    }
}

@BindingAdapter("app:adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
}