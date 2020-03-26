package com.maseletrico.coronavirus.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maseletrico.coronavirus.R
import com.maseletrico.coronavirus.util.CountryCode
import com.maseletrico.coronavirus.viewModel.CountryStatsViewModel
import kotlinx.android.synthetic.main.frag_country_layout.*
import java.util.*


class CountryFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_country_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var countries = mutableSetOf<String>()
        for (locale in Locale.getAvailableLocales()) {
            if (!TextUtils.isEmpty(locale.displayCountry)) {
                countries.add(locale.displayCountry)
            }
        }

        val spinnerAdapter = ArrayAdapter<String>(this.requireContext(),android.R.layout.simple_spinner_item, countries.sorted())
         spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_country!!.adapter = spinnerAdapter


        val viewModelCountryStats: CountryStatsViewModel = ViewModelProvider(this).get(
            CountryStatsViewModel::class.java)

        viewModelCountryStats.countryCoronaStats.observe(viewLifecycleOwner, Observer {
            it?.let { countryStatistic ->
                txv_total_cases_answer.text = countryStatistic[0].totalCases.toString()
                txv_recovered_answer.text = countryStatistic[0].totalRecovered.toString()
                txv_deaths_answer.text = countryStatistic[0].totalDeaths.toString()
                txv_new_cases_today_answer.text = countryStatistic[0].totalNewCasesToday.toString()
                txv_new_deaths_today_answer.text = countryStatistic[0].totalNewDeathsToday.toString()
                txv_active_cases_answer.text = countryStatistic[0].totalActiveCases.toString()
                txv_serious_cases_answer.text = countryStatistic[0].totalSeriousCases.toString()
            }
        })

        viewModelCountryStats.deathRate.observe(viewLifecycleOwner, Observer{
            if(it == "vazio"){
                setFieldsToZero()
            }else{
                txv_death_rate_answer.text =  it
            }

        })

        viewModelCountryStats.worldVirusStats.observe(viewLifecycleOwner, Observer {
            it.let { worldStatistics ->
                Log.i("VIRUSSTATS: ",worldStatistics[0].toString())
            }

        })

        spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val country = parent?.let {
                    val mCountry = getCountryCode(it.selectedItem.toString())
                    val countryCode = Locale("",mCountry)
                    if (mCountry != null) {
                        CountryCode( mCountry)

                    }
                    mCountry?.let { countryCodeSelected -> viewModelCountryStats.getCountryStats(countryCodeSelected) }
                    viewModelCountryStats.getGlobalStats()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun setFieldsToZero() {
        txv_death_rate_answer.text =  ""
        txv_total_cases_answer.text = ""
        txv_recovered_answer.text = ""
        txv_deaths_answer.text = ""
        txv_new_cases_today_answer.text = ""
        txv_new_deaths_today_answer.text = ""
        txv_active_cases_answer.text = ""
        txv_serious_cases_answer.text = ""
        Toast.makeText(context,getString(R.string.country_not_found),Toast.LENGTH_LONG).show()
    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find {
            Locale("", it).displayCountry == countryName
        }

}