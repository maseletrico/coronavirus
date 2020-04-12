package com.maseletrico.coronavirus.data.model.novelHystorical


import com.google.gson.annotations.SerializedName

data class Timeline(
    @SerializedName("cases")
    val cases: Map<String, Int>,
    @SerializedName("deaths")
    val deaths: Map<String, Int>,
    @SerializedName("recovered")
    val recovered: Map<String, Int>
)