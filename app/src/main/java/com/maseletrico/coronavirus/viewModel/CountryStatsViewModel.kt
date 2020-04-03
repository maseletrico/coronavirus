package com.maseletrico.coronavirus.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maseletrico.coronavirus.data.ApiService
import com.maseletrico.coronavirus.data.model.CoronaStats
import com.maseletrico.coronavirus.data.model.CoronaWorldStats
import com.maseletrico.coronavirus.data.model.Countrydata
import com.maseletrico.coronavirus.data.model.GlobalData
import com.maseletrico.coronavirus.data.model.novelCovid.novelByCountry
import com.maseletrico.coronavirus.data.model.timeline.CoronaTimeline
import com.maseletrico.coronavirus.data.model.timeline.Timelineitem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryStatsViewModel : ViewModel() {
   val countryCoronaStats: MutableLiveData<List<Countrydata>> = MutableLiveData<List<Countrydata>>()
    val worldVirusStats: MutableLiveData<List<GlobalData>> = MutableLiveData<List<GlobalData>>()
    val apiErr: MutableLiveData<String> = MutableLiveData("valor inicial")
    var deathRate = MutableLiveData<String>()
    var percent: Double = 0.0
    var novelCountryResponse: MutableLiveData<novelByCountry> = MutableLiveData<novelByCountry>()

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
                        if(coronaStatsResponse.countrydata != null) {
                            percent = with(coronaStatsResponse.countrydata[0]) {
                                totalDeaths.toDouble() * 100 / totalCases.toDouble()
                            }
                            var number2digits = String.format("%.2f", percent).toString()
                            deathRate.value = number2digits
                            stats.value = coronaStatsResponse.countrydata
                            countryCoronaStats.value = stats.value
                        }else{
                           deathRate.value = "vazio"
                        }
                     }
                }
            }
        })
        return stats
    }

    fun getCountryTimeline(currentCountryCode: String?): MutableLiveData<List<Timelineitem>> {
        val timelineStats: MutableLiveData<List<Timelineitem>> = MutableLiveData()
        ApiService.service.timelineList(currentCountryCode).enqueue(object: Callback<CoronaTimeline> {

            override fun onFailure(call: Call<CoronaTimeline>, t: Throwable) {
                apiErr.value = "Erro ao carregar API"
                Log.e("API STAT ", t.toString())
            }

            override fun onResponse(
                call: Call<CoronaTimeline>,response: Response<CoronaTimeline>) {
                if(response.body() != null){
                    response.body().let { coronaTimelineResponse ->
                        timelineStats.value = coronaTimelineResponse?.timelineitems
                    }
                }
            }

        })
        return timelineStats
    }

    fun getGlobalStats (): MutableLiveData<List<GlobalData>>{
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

    fun getNovelCountryStats(novelCountry: String): MutableLiveData<novelByCountry>{
        val novelCountryStats: MutableLiveData<novelByCountry> = MutableLiveData()
        ApiService.serviceNovel.novelCountryInfo(novelCountry).enqueue(object: Callback<novelByCountry> {
            override fun onFailure(call: Call<novelByCountry>, t: Throwable) {
                apiErr.value = "Erro ao carregar API Novel"
                Log.e("API STAT WORLD ", t.toString())
            }

            override fun onResponse(
                call: Call<novelByCountry>, response: Response<novelByCountry>
            ) {
                response.body().let { novelCountryStatistic ->
                    novelCountryStats.value = novelCountryStatistic
                    novelCountryResponse.value = novelCountryStatistic
                }
            }

        })
        return novelCountryStats
    }

}