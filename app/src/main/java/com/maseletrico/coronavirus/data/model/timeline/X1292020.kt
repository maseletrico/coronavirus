package com.maseletrico.coronavirus.data.model.timeline


import com.google.gson.annotations.SerializedName

data class X1292020(
    @SerializedName("new_daily_cases")
    val newDailyCases: Int,
    @SerializedName("new_daily_deaths")
    val newDailyDeaths: Int,
    @SerializedName("total_cases")
    val totalCases: Int,
    @SerializedName("total_recoveries")
    val totalRecoveries: Int,
    @SerializedName("total_deaths")
    val totalDeaths: Int
)