package com.maseletrico.coronavirus.data.model


import com.google.gson.annotations.SerializedName

data class CoronaWorldStats(
    @SerializedName("results")
    val results: List<GlobalData>,
    @SerializedName("stat")
    val stat: String
)