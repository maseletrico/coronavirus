package com.maseletrico.coronavirus.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.maseletrico.coronavirus.R
import com.maseletrico.coronavirus.data.model.novelHystorical.Timeline
import com.maseletrico.coronavirus.util.CountryCode
import com.maseletrico.coronavirus.viewModel.CountryStatsViewModel
import kotlinx.android.synthetic.main.frag_global_layout.*

class GlobalFragment : Fragment() {

    lateinit var country: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_global_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val casesChartView = this.cases_graph
        val deathsChartView = this.deaths_graph
        val casesDailyChartView = this.cases_daily_graph
        val deathsDailyChartView = this.deaths_daily_graph

        val viewModelCountryStats: CountryStatsViewModel = ViewModelProvider(this).get(
            CountryStatsViewModel::class.java
        )

        country = getPreference()
        viewModelCountryStats.getCountryHistorical(country)

        viewModelCountryStats.novelCountryHistorical.observe(viewLifecycleOwner, Observer {

            setCaseGraph(casesChartView, it)
            setDeathGraph(deathsChartView, it)
            setCasesDailyGraph(casesDailyChartView, it)
            setDeathsDailyGraph(deathsDailyChartView, it)

        })


    }

    private fun setCaseGraph(graph: LineChart, response: Timeline) {
        var index = 0.0f
        val days: MutableList<Float> = mutableListOf()
        val historicalCases: MutableList<Float> = mutableListOf()
        val historicalDeaths: MutableList<Float> = mutableListOf()
        val historicalRecovered: MutableList<Float> = mutableListOf()


        for (numberOfCases in response.cases) {
            historicalCases.add(numberOfCases.value.toFloat())
            days.add(index++)
        }
        for (numberOfDeaths in response.deaths.values) {
            historicalDeaths.add(numberOfDeaths.toFloat())
            days.add(index++)
        }
        for (numberOfRecovered in response.recovered.values) {
            historicalRecovered.add(numberOfRecovered.toFloat())
            days.add(index++)
        }

        val entryCases = historicalCases.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        val entryDeaths = historicalDeaths.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        val entryRecovered = historicalRecovered.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        val lineDataSet1 = LineDataSet(entryCases, "Cases")
        with(lineDataSet1) {
            this.color = Color.RED
            this.setDrawCircles(false)
            this.setDrawValues(false)
            this.axisDependency = YAxis.AxisDependency.LEFT
        }

        val lineDataSet2 = LineDataSet(entryDeaths, "Deaths")
        with(lineDataSet2) {
            this.color = Color.BLUE
            this.setDrawCircles(false)
            this.setDrawValues(false)
            this.axisDependency = YAxis.AxisDependency.LEFT
        }

        val lineDataSet3 = LineDataSet(entryRecovered, "Recovered")
        with(lineDataSet3) {
            this.color = Color.DKGRAY
            this.setDrawCircles(false)
            this.setDrawValues(false)
            this.axisDependency = YAxis.AxisDependency.LEFT
        }

        val lineDataSets = arrayListOf(lineDataSet1, lineDataSet2, lineDataSet3)

        with(graph) {
            this.data = LineData(lineDataSets as List<ILineDataSet>?)
            this.description.text = country

        }

    }

    private fun setDeathGraph(graph: LineChart, response: Timeline) {

        var index = 0.0f
        var days: MutableList<Float> = mutableListOf()
        var historicalDeaths: MutableList<Float> = mutableListOf()
        for (numberOfCases in response.deaths.values) {
            historicalDeaths.add(numberOfCases.toFloat())
            days.add(index++)
        }
        val entryCases = historicalDeaths.mapIndexed { index, arrayList ->
            Entry(index.toFloat(), arrayList)
        }

        graph.setBackgroundColor(Color.WHITE)

        val lineDataSet1 = LineDataSet(entryCases, "Deaths")
        lineDataSet1.color = Color.BLUE
        lineDataSet1.setDrawCircles(false)
        lineDataSet1.setDrawValues(false)
        lineDataSet1.axisDependency = YAxis.AxisDependency.LEFT

        val lineDataSets = arrayListOf(lineDataSet1)

        with(graph) {
            this.data = LineData(lineDataSets as List<ILineDataSet>?)
            this.description.text = country
        }

    }

    private fun setCasesDailyGraph(graph: BarChart, response: Timeline) {

        val historicalDailyCases: MutableList<Float> = mutableListOf()
        var index = 0.0f
        var indexInt = 0
        var days: MutableList<Float> = mutableListOf()
        var historicalCases: MutableList<Float> = mutableListOf()

        for (numberOfCases in response.cases.values) {
            historicalCases.add(numberOfCases.toFloat())
            days.add(index++)
            indexInt++
            if (indexInt > 2) {
                historicalDailyCases.add(historicalCases[indexInt - 1] - historicalCases[indexInt - 2])
            }
        }

        val entryCasesDaily = historicalDailyCases.mapIndexed { index, arrayList ->
            BarEntry(index.toFloat(), arrayList)
        }

        graph.setBackgroundColor(Color.WHITE)

        val barDataSet1 = BarDataSet(entryCasesDaily, "Daily Cases")
        barDataSet1.color = Color.BLUE
        barDataSet1.setDrawValues(false)
        barDataSet1.axisDependency = YAxis.AxisDependency.LEFT


        val lineDataSets = arrayListOf(barDataSet1)

        with(graph) {
            this.data = BarData(lineDataSets as List<BarDataSet>?)
            this.description.text = country
        }

    }

    private fun setDeathsDailyGraph(graph: BarChart, response: Timeline) {

        val historicalDeathCases: MutableList<Float> = mutableListOf()
        var index = 0.0f
        var indexInt = 0
        var days: MutableList<Float> = mutableListOf()
        var historicalDeaths: MutableList<Float> = mutableListOf()

        for (numberOfCases in response.deaths.values) {
            historicalDeaths.add(numberOfCases.toFloat())
            days.add(index++)
            indexInt++
            if (indexInt > 2) {
                historicalDeathCases.add(historicalDeaths[indexInt - 1] - historicalDeaths[indexInt - 2])
            }
        }

        val entryDeathsDaily = historicalDeathCases.mapIndexed { index, arrayList ->
            BarEntry(index.toFloat(), arrayList)
        }

        graph.setBackgroundColor(Color.WHITE)

        val barDataSet1 = BarDataSet(entryDeathsDaily, "Daily Deaths")
        barDataSet1.color = Color.BLUE
        barDataSet1.setDrawValues(false)
        barDataSet1.axisDependency = YAxis.AxisDependency.LEFT

        val lineDataSets = arrayListOf(barDataSet1)

        with(graph) {
            this.data = BarData(lineDataSets as List<BarDataSet>?)
            this.description.text = country
        }

    }

    private fun savePreference(country: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.saved_country), country)
            commit()
        }
    }

    private fun getPreference(): String {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = getString(R.string.saved_country_default_key)
        val country = sharedPref?.getString(getString(R.string.saved_country), defaultValue)
        return country.toString()
    }
}