package com.maseletrico.coronavirus.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.maseletrico.coronavirus.R
import com.maseletrico.coronavirus.data.entities.FavoriteCountriesEntity
import com.maseletrico.coronavirus.data.room.AppDatabase
import com.maseletrico.coronavirus.data.room.FavoriteCountriesDao
import com.maseletrico.coronavirus.viewModel.CountryStatsViewModel
import kotlinx.android.synthetic.main.frag_country_layout.*
import java.text.SimpleDateFormat
import java.util.*


class CountryFragment : Fragment() {

    private var db: AppDatabase? = null
    private var favoriteCountriesDao: FavoriteCountriesDao? = null
    private var countries = mutableSetOf<String>()
    private lateinit var countrySaved: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get list of countries from android system
        for (locale in Locale.getAvailableLocales()) {
            if (!TextUtils.isEmpty(locale.displayCountry)) {
                countries.add(locale.displayCountry)
            }
        }

        countrySaved = getPreference()

        return inflater.inflate(R.layout.frag_country_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        db = AppDatabase.getAppDatabase(viewLifecycleOwner)
        favoriteCountriesDao = db?.favoriteCountriesDao()

        val favoriteCountry1 =
            FavoriteCountriesEntity(
                1, "Brazil",
                "UnitedStates",
                "Italy",
                "China",
                "Spain"
            )
        favoriteCountriesDao?.insertFavoriteCountry(favoriteCountry1)

        val countryLIst: List<FavoriteCountriesEntity>? =
            favoriteCountriesDao?.getFavoriteCountries()

        countryLIst?.forEach {
            println(it)
        }


        val spinnerAdapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            countries.sorted()
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_country!!.adapter = spinnerAdapter

        countries.sorted().forEachIndexed { index: Int, s: String ->
            if (getCountryCode(s) == countrySaved) {
                spinner_country.setSelection(index)
                //return@forEachIndexed
            }
        }

        //instancia view model
        val viewModelCountryStats: CountryStatsViewModel = ViewModelProvider(this).get(
            CountryStatsViewModel::class.java
        )


        viewModelCountryStats.getCountryTimeline(countrySaved)

        viewModelCountryStats.countryCoronaStats.observe(viewLifecycleOwner, Observer {
            it?.let { countryStatistic ->
                txv_total_cases_answer.text = countryStatistic[0].totalCases.toString()
                txv_recovered_answer.text = countryStatistic[0].totalRecovered.toString()
                txv_deaths_answer.text = countryStatistic[0].totalDeaths.toString()
                txv_new_cases_today_answer.text = countryStatistic[0].totalNewCasesToday.toString()
                txv_new_deaths_today_answer.text =
                    countryStatistic[0].totalNewDeathsToday.toString()
                txv_active_cases_answer.text = countryStatistic[0].totalActiveCases.toString()
                txv_serious_cases_answer.text = countryStatistic[0].totalSeriousCases.toString()
                txv_danger_rank_answer.text = countryStatistic[0].totalDangerRank.toString()
                loader_icon.visibility = View.GONE
            }
        })

        viewModelCountryStats.deathRate.observe(viewLifecycleOwner, Observer {
            if (it == "vazio") {
                setFieldsToZero()
            } else {
                txv_death_rate_answer.text = it
            }

        })

        viewModelCountryStats.worldVirusStats.observe(viewLifecycleOwner, Observer {
            it.let { worldStatistics ->
                worldStatistics?.let { countryStatistic ->
                    txv_total_cases_answer.text = countryStatistic[0].totalCases.toString()
                    txv_recovered_answer.text = countryStatistic[0].totalRecovered.toString()
                    txv_deaths_answer.text = countryStatistic[0].totalDeaths.toString()
                    txv_new_cases_today_answer.text =
                        countryStatistic[0].totalNewCasesToday.toString()
                    txv_new_deaths_today_answer.text =
                        countryStatistic[0].totalNewDeathsToday.toString()
                    txv_active_cases_answer.text = countryStatistic[0].totalActiveCases.toString()
                    txv_serious_cases_answer.text = countryStatistic[0].totalSeriousCases.toString()
                }
            }

        })

        viewModelCountryStats.novelCountryResponse.observe(viewLifecycleOwner, Observer {
            it.let { novelStatisticByCountry ->
                val date = Date(novelStatisticByCountry.updated)
                val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
                txv_updated.text = format.format(date).toString()
                txv_total_cases_answer.text = novelStatisticByCountry.cases.toString()
                txv_recovered_answer.text = novelStatisticByCountry.recovered.toString()
                txv_deaths_answer.text = novelStatisticByCountry.deaths.toString()
                txv_new_cases_today_answer.text = novelStatisticByCountry.todayCases.toString()
                txv_new_deaths_today_answer.text = novelStatisticByCountry.todayDeaths.toString()
                txv_active_cases_answer.text = novelStatisticByCountry.active.toString()
                txv_serious_cases_answer.text = novelStatisticByCountry.critical.toString()
                txv_cases_per_million_answer.text =
                    novelStatisticByCountry.casesPerOneMillion.toString()
                txv_deaths_per_million_answer.text =
                    novelStatisticByCountry.deathsPerOneMillion.toString()

                Glide.with(imv_flag)
                    .load(novelStatisticByCountry.countryInfo.flag)
                    .fallback(R.drawable.ic_flag_black_24dp)
                    .into(imv_flag)

            }
        })

        spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val country = parent?.let {
                    val mCountry =
                        getCountryCode(it.selectedItem.toString()).let { countryCodeSelected ->
                            countryCodeSelected?.let { selectedCountry ->
                                viewModelCountryStats.getNovelCountryStats(countryCodeSelected)
                                viewModelCountryStats.getCountryHistorical(countryCodeSelected)
                                savePreference(selectedCountry)
                            }
                            viewModelCountryStats.getNovelCountries()
                            loader_icon.visibility = View.VISIBLE
                        }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("ok")
            }

        }

        imv_favorite1.setOnClickListener{

        }
//        imv_favorite1.setOnLongClickListener {
//
//        }
    }


    private fun setFieldsToZero() {
        txv_death_rate_answer.text = ""
        txv_total_cases_answer.text = ""
        txv_recovered_answer.text = ""
        txv_deaths_answer.text = ""
        txv_new_cases_today_answer.text = ""
        txv_new_deaths_today_answer.text = ""
        txv_active_cases_answer.text = ""
        txv_serious_cases_answer.text = ""
        loader_icon.visibility = View.GONE
        Toast.makeText(context, getString(R.string.country_not_found), Toast.LENGTH_LONG).show()
    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find {
            Locale("", it).displayCountry == countryName
        }

    private fun savePreference(countryPref: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.saved_country), countryPref)
            commit()
        }
    }

    private fun getPreference(): String {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = resources.getString(R.string.saved_country_default_key)
        val country = sharedPref?.getString(getString(R.string.saved_country), defaultValue)
        return country.toString()
    }
}

