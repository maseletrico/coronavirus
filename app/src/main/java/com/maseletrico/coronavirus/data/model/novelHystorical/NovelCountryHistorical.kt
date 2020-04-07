package com.maseletrico.coronavirus.data.model.novelHystorical


import com.google.gson.annotations.SerializedName

data class NovelCountryHistorical(
    @SerializedName("country")
    val country: String,
    @SerializedName("provinces")
    val provinces: List<String>,
    @SerializedName("timeline")
    val timeline: Timeline
)