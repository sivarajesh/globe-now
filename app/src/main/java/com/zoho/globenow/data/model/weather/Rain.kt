package com.zoho.globenow.data.model.weather


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double = 0.0
)