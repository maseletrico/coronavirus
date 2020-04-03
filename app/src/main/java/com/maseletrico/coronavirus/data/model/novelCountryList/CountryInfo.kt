package com.maseletrico.coronavirus.data.model.novelCountryList


import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("iso2")
    val iso2: String,
    @SerializedName("iso3")
    val iso3: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("long")
    val long: Int,
    @SerializedName("flag")
    val flag: String
)