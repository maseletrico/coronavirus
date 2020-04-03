package com.maseletrico.coronavirus.data

import com.maseletrico.coronavirus.data.model.CoronaStats
import com.maseletrico.coronavirus.data.model.CoronaWorldStats
import com.maseletrico.coronavirus.data.model.novelCovid.novelByCountry
import com.maseletrico.coronavirus.data.model.timeline.CoronaTimeline
import com.maseletrico.coronavirus.util.ApiConstants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//import java.awt.print.Book





interface CountryStatApiService {
    //https://thevirustracker.com/free-api?countryTotal=ES
    @GET(ApiConstants.API_URL)
    fun listRepos(
        @Query("countryTotal") location: String? = ApiConstants.API_COUNTRY//currentCode.code
    ): Call<CoronaStats>

    //https://thevirustracker.com/free-api?global=stats
    @GET(ApiConstants.API_URL)
    fun worldList(
        @Query("global") location: String? = "stats"
    ): Call<CoronaWorldStats>

    //https://thevirustracker.com/free-api?countryTimeline=BR
    @GET(ApiConstants.API_URL)
    fun timelineList(
        @Query("countryTimeLine/") location: String? = ApiConstants.API_COUNTRY
    ): Call<CoronaTimeline>

    //novel Covid Api  https://corona.lmao.ninja/countries/BR
    //                  https://corona.lmao.ninja/AF}
    @GET("countries/{countryCodeIso}")
    fun novelCountryInfo(
        @Path("countryCodeIso")fullUrl: String
    ): Call<novelByCountry>

//    @GET("/image/{id}")
//    fun example(@Path("id") id: Int): Call<ResponseBody?>?
}


