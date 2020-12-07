package com.zoho.globenow.data.model.country


import com.google.gson.annotations.SerializedName

data class RegionalBloc(
    @SerializedName("acronym")
    val acronym: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("otherAcronyms")
    val otherAcronyms: List<Any> = listOf(),
    @SerializedName("otherNames")
    val otherNames: List<Any> = listOf()
)