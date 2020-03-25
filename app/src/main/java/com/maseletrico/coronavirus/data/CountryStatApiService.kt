package com.maseletrico.coronavirus.data

import com.maseletrico.coronavirus.data.model.CoronaStats
import com.maseletrico.coronavirus.util.ApiConstants
import com.maseletrico.coronavirus.util.CountryCode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CountryStatApiService {

    @GET(ApiConstants.API_URL)
    fun listRepos(
        @Query("countryTotal") location: String? = ApiConstants.API_COUNTRY//currentCode.code
    ): Call<CoronaStats>
}


