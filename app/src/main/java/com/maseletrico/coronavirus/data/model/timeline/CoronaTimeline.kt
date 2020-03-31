package com.maseletrico.coronavirus.data.model.timeline


import com.google.gson.annotations.SerializedName

data class CoronaTimeline(
    @SerializedName("countrytimelinedata")
    val countrytimelinedata: List<Countrytimelinedata>,
    @SerializedName("timelineitems")
    val timelineitems: List<Timelineitem>
)