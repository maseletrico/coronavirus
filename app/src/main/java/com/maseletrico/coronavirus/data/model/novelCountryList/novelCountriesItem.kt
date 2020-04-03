package com.maseletrico.coronavirus.data.model.novelCountryList


import com.google.gson.annotations.SerializedName

data class novelCountriesItem(
    @SerializedName("country")
    val country: String,
    @SerializedName("countryInfo")
    val countryInfo: CountryInfo,
    @SerializedName("cases")
    val cases: Int,
    @SerializedName("todayCases")
    val todayCases: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("todayDeaths")
    val todayDeaths: Int,
    @SerializedName("recovered")
    val recovered: Int,
    @SerializedName("active")
    val active: Int,
    @SerializedName("critical")
    val critical: Int,
    @SerializedName("casesPerOneMillion")
    val casesPerOneMillion: Int,
    @SerializedName("deathsPerOneMillion")
    val deathsPerOneMillion: Int,
    @SerializedName("updated")
    val updated: Int
)