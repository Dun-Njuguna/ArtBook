package com.example.artbook.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.artbook.R
import com.example.artbook.util.NetworkLiveData
import com.example.artbook.view.fragment.ArtFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var artFragmentFactory: ArtFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = artFragmentFactory
        setContentView(R.layout.activity_main)
        observeNetworkState()
    }

    private fun observeNetworkState() {
        if (!NetworkLiveData.hasActiveObservers()) {
            NetworkLiveData.observe(this) { status ->

            }
        }
    }

}