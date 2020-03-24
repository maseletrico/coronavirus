package com.maseletrico.coronavirus.data.model


import com.google.gson.annotations.SerializedName
import com.maseletrico.coronavirus.util.*

data class Countrynewsitem(
    @SerializedName("stat")
    val stat: String
)