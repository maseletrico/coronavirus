package com.maseletrico.coronavirus.data.model.timeline


import com.google.gson.annotations.SerializedName

data class Timelineitem(
    @SerializedName("1/29/2020")
    val x1292020: X1292020,
    @SerializedName("stat")
    val stat: String
)