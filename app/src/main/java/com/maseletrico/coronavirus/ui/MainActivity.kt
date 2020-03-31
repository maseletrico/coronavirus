package com.maseletrico.coronavirus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.maseletrico.coronavirus.R
import com.maseletrico.coronavirus.ui.fragments.CountryFragment
import com.maseletrico.coronavirus.ui.fragments.GlobalFragment
import com.maseletrico.coronavirus.viewModel.CountryStatsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val countryFragment = CountryFragment()
    private val globalFragment = GlobalFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callFragCountry()

        val viewModelCountryStats: CountryStatsViewModel = ViewModelProvider(this).get(
            CountryStatsViewModel::class.java
        )

        smooth_bottom_bar.setOnItemSelectedListener(object: OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                when(pos){
                    0 ->  viewModelCountryStats.getCountryStats("BR")
                    1 ->  viewModelCountryStats.getGlobalStats()
                }
            }
        })
    }

    private fun callFragCountry() {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame,countryFragment)
        transaction.commit()
    }

    private fun callFragGlobal() {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame,globalFragment)
        transaction.commit()
    }

    private fun callFragTimeline() {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame,countryFragment)
        transaction.commit()
    }

}
