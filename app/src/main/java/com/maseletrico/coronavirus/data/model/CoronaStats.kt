package com.maseletrico.coronavirus.data.model


import com.google.gson.annotations.SerializedName

data class CoronaStats(
    @SerializedName("countrydata")
    val countrydata: List<Countrydata>,
    @SerializedName("countrynewsitems")
    val countrynewsitems: List<Countrynewsitem>
)