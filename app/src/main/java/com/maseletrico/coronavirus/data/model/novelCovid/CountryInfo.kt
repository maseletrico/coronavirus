package com.maseletrico.coronavirus.data.model.novelCovid


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
    val lat: Number,
    @SerializedName("long")
    val long: Number,
    @SerializedName("flag")
    val flag: String
)