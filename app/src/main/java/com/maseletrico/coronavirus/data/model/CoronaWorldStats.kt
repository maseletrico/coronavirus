package com.maseletrico.coronavirus.data.model


import com.google.gson.annotations.SerializedName

data class CoronaWorldStats(
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("stat")
    val stat: String
)