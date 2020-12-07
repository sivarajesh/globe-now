package com.zoho.globenow.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country", indices = [Index("name", unique = true)])
data class CountryEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,
    @SerializedName("area")
    @ColumnInfo(name = "area")
    val area: Double,
    @SerializedName("population")
    @ColumnInfo(name = "population")
    val population: Int,
    @SerializedName("flag")
    @ColumnInfo(name = "flag")
    val flag: String,
    @SerializedName("country_code")
    @ColumnInfo(name = "countryCode")
    val countryCode: String,
    @SerializedName("capital")
    @ColumnInfo(name = "capital")
    val capital: String,
    @SerializedName("region")
    @ColumnInfo(name = "region")
    val region: String,
    @SerializedName("lat")
    @ColumnInfo(name = "lat")
    val lat: Double?,
    @SerializedName("lng")
    @ColumnInfo(name = "lng")
    val lng: Double?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeDouble(area)
        parcel.writeInt(population)
        parcel.writeString(flag)
        parcel.writeString(countryCode)
        parcel.writeString(capital)
        parcel.writeString(region)
        if (lat != null) {
            parcel.writeDouble(lat)
        }
        if (lng != null) {
            parcel.writeDouble(lng)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryEntity> {
        override fun createFromParcel(parcel: Parcel): CountryEntity {
            return CountryEntity(parcel)
        }

        override fun newArray(size: Int): Array<CountryEntity?> {
            return arrayOfNulls(size)
        }
    }

}