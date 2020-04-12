package com.maseletrico.coronavirus.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maseletrico.coronavirus.data.ApiService
import com.maseletrico.coronavirus.data.model.CoronaStats
import com.maseletrico.coronavirus.data.model.CoronaWorldStats
import com.maseletrico.coronavirus.data.model.Countrydata
import com.maseletrico.coronavirus.data.model.GlobalData
import com.maseletrico.coronavirus.data.model.novelCountryList.NovelCountries
import com.maseletrico.coronavirus.data.model.novelCountryList.NovelCountriesItem
import com.maseletrico.coronavirus.data.model.novelCovid.NovelByCountry
import com.maseletrico.coronavirus.data.model.novelHystorical.NovelCountryHistorical
import com.maseletrico.coronavirus.data.model.novelHystorical.Timeline
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryStatsViewModel : ViewModel() {
    val countryCoronaStats: MutableLiveData<List<Countrydata>> =
        MutableLiveData<List<Countrydata>>()
    val worldVirusStats: MutableLiveData<List<GlobalData>> = MutableLiveData<List<GlobalData>>()
    val apiErr: MutableLiveData<String> = MutableLiveData("valor inicial")
    var deathRate = MutableLiveData<String>()
    var percent: Double = 0.0
    var novelCountryResponse: MutableLiveData<NovelByCountry> = MutableLiveData<NovelByCountry>()
    var novelCountriesResponse: MutableLiveData<ArrayList<NovelCountriesItem>> =
        MutableLiveData<ArrayList<NovelCountriesItem>>()
    var novelCountryHistorical: MutableLiveData<Timeline> = MutableLiveData()
    var currentCountryCode = MutableLiveData<String>()

    fun getCountryStats(currentCountryCode: String?): MutableLiveData<List<Countrydata>> {
        val stats: MutableLiveData<List<Countrydata>> = MutableLiveData()
        ApiService.service.listRepos(currentCountryCode).enqueue(object : Callback<CoronaStats> {
            override fun onFailure(call: Call<CoronaStats>, t: Throwable) {
                apiErr.value = "Erro ao carregar API"
                Log.e("API STAT ", t.toString())
            }

            override fun onResponse(call: Call<CoronaStats>, response: Response<CoronaStats>) {
                if (response.isSuccessful) {
                    response.body()?.let { coronaStatsResponse ->
                        if (coronaStatsResponse.countrydata != null) {
                            percent = with(coronaStatsResponse.countrydata[0]) {
                                totalDeaths.toDouble() * 100 / totalCases.toDouble()
                            }
                            var number2digits = String.format("%.2f", percent).toString()
                            deathRate.value = number2digits
                            stats.value = coronaStatsResponse.countrydata
                            countryCoronaStats.value = stats.value
                        } else {
                            deathRate.value = "vazio"
                        }
                    }
                }
            }
        })
        return stats
    }

    fun getGlobalStats(): MutableLiveData<List<GlobalData>> {
        val globalStats: MutableLiveData<List<GlobalData>> = MutableLiveData()
        ApiService.service.worldList().enqueue(object : Callback<CoronaWorldStats> {
            override fun onFailure(call: Call<CoronaWorldStats>, t: Throwable) {
                apiErr.value = "Erro ao carregar API"
                Log.e("API STAT WORLD ", t.toString())
            }

            override fun onResponse(
                call: Call<CoronaWorldStats>, response: Response<CoronaWorldStats>
            ) {
                response.body().let { worldStatsResponse ->
                    globalStats.value = worldStatsResponse?.results
                    worldVirusStats.value = globalStats.value
                }
            }

        })
        return globalStats
    }

    fun getNovelCountryStats(novelCountry: String): MutableLiveData<NovelByCountry> {
        val novelCountryStats: MutableLiveData<NovelByCountry> = MutableLiveData()
        ApiService.serviceNovel.novelCountryInfo(novelCountry)
            .enqueue(object : Callback<NovelByCountry> {
                override fun onFailure(call: Call<NovelByCountry>, t: Throwable) {
                    apiErr.value = "Erro ao carregar API Novel"
                    Log.e("API STAT WORLD ", t.toString())
                }

                override fun onResponse(
                    call: Call<NovelByCountry>, response: Response<NovelByCountry>
                ) {
                    response.body().let { novelCountryStatistic ->
                        novelCountryStats.value = novelCountryStatistic
                        novelCountryResponse.value = novelCountryStatistic
                    }
                }

            })
        return novelCountryStats
    }

    fun getNovelCountries(): MutableLiveData<ArrayList<NovelCountriesItem>> {
        val novelCountriesItems: MutableLiveData<ArrayList<NovelCountriesItem>> = MutableLiveData()
        ApiService.serviceNovel.novelCountries().enqueue(object : Callback<NovelCountries> {
            override fun onFailure(call: Call<NovelCountries>, t: Throwable) {
                apiErr.value = "Erro ao carregar API Novel"
                Log.e("API NOVEL COUNTRIES ", t.toString())
            }

            override fun onResponse(
                call: Call<NovelCountries>,
                response: Response<NovelCountries>
            ) {
                response.body().let { novelCountriesAnswer ->
                    novelCountriesItems.value = novelCountriesAnswer
                    novelCountriesResponse.value = novelCountriesItems.value
                }
            }

        })
        return novelCountriesItems
    }

    fun getCountryHistorical(novelCountry: String): MutableLiveData<Timeline> {
        ApiService.serviceNovel.novelCountryHistorical(novelCountry)
            .enqueue(object : Callback<NovelCountryHistorical> {

                override fun onFailure(call: Call<NovelCountryHistorical>, t: Throwable) {
                    apiErr.value = "Erro ao carregar API Novel"
                    Log.e("COUNTRIES HISTORICAL", t.toString())
                }

                override fun onResponse(
                    call: Call<NovelCountryHistorical>,
                    response: Response<NovelCountryHistorical>
                ) {
                    response.body()?.let { novelHistoric ->
                        novelCountryHistorical.value = novelHistoric.timeline
                    }
                }

            })
        return novelCountryHistorical
    }

//    fun setCurrentCountryCode(code: String){
//        currentCountryCode.value = code
//        val xx = currentCountryCode.value
//    }
//
//    fun getCurrentCountryCode(): String?{
//        return currentCountryCode.value
//    }

}