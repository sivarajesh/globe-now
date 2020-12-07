package com.zoho.globenow.data.model.country


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("symbol")
    val symbol: String = ""
)