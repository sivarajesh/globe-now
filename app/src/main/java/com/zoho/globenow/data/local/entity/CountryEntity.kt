package com.zoho.globenow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country")
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
    val flag: String)