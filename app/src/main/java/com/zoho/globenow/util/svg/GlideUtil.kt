package com.zoho.globenow.util.svg

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object GlideUtil {

    fun glideBuilder(context: Context): GlideRequest<PictureDrawable> {
        return GlideApp.with(context)
            .`as`(PictureDrawable::class.java)
            .placeholder(android.R.drawable.ic_menu_upload)
            .error(android.R.drawable.alert_dark_frame)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(SvgSoftwareLayerSetter())
    }
}