package com.maseletrico.coronavirus.data

import com.maseletrico.coronavirus.util.ApiConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private fun initRetrofit(): Retrofit{
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(1000,TimeUnit.MILLISECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_CORONAVIRUS_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val service: CountryStatApiService = initRetrofit().create(CountryStatApiService::class.java)
}