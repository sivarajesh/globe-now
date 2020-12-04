package com.zoho.globenow.di

import android.R
import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.StreamEncoder
import com.caverock.androidsvg.SVG
import com.zoho.globenow.data.local.GlobeDB
import com.zoho.globenow.data.local.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): GlobeDB {
        return Room.databaseBuilder(
            appContext,
            GlobeDB::class.java, GlobeDB.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCountryDao(database: GlobeDB): CountryDao {
        return database.countryDao()
    }

    @Provides
    fun provideGlide(): GenericRequestBuilder{
        return Glide.with(mActivity)
            .using(
                Glide.buildStreamModelLoader(Uri::class.java, mActivity),
                InputStream::class.java
            )
            .from(Uri::class.java)
            .`as`(SVG::class.java)
            .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
            .sourceEncoder(StreamEncoder())
            .cacheDecoder(FileToStreamDecoder<SVG>(SvgDecoder()))
            .decoder(SvgDecoder())
            .placeholder(R.drawable.ic_facebook)
            .error(R.drawable.ic_web)
            .animate(R.anim.fade_in)
            .listener(SvgSoftwareLayerSetter<Uri>()).also { requestBuilder = it }
    }
}