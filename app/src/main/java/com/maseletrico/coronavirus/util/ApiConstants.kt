package com.maseletrico.coronavirus.util

class ApiConstants {
    // country ststs    url: 'https://thevirustracker.com/free-api?countryTotal=US'
    // country timeline url: 'https://thevirustracker.com/free-api?countryTimeline=US'
    // global stats     url: 'https://thevirustracker.com/free-api?global=stats',

    companion object{
        const val BASE_CORONAVIRUS_API = "https://thevirustracker.com/"
        const val API_URL = "free-api"
        const val API_COUNTRY = "BR"
        const val SHARED_PREFS_COUNTRY_CHOICE = "countryChoice"
        //novel Covid Api  https://corona.lmao.ninja/countries/BR
        const val BASE_NOVEL_COVID_API = "https://corona.lmao.ninja/"
        const val NOVEL_URL_COUNTRIES = "countries/"

    }

}