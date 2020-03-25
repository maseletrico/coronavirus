package com.maseletrico.coronavirus.data.model


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("url")
    val url: String
)