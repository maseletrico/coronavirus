package com.maseletrico.coronavirus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maseletrico.coronavirus.R
import com.maseletrico.coronavirus.ui.fragments.CountryFragment
import com.maseletrico.coronavirus.ui.fragments.GlobalFragment
import com.maseletrico.coronavirus.viewModel.CountryStatsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemReselectedListener
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val countryFragment = CountryFragment()
    private val globalFragment = GlobalFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callFragGlobal()

        smooth_bottom_bar.setOnItemSelectedListener(object: OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                when(pos){
                    0 ->  callFragTimeline()
                    1 ->  callFragCountry()
                    2 ->  callFragGlobal()
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
