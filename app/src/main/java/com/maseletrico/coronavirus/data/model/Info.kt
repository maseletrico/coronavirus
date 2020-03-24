package com.maseletrico.coronavirus.data.model


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("ourid")
    val ourid: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("source")
    val source: String
)