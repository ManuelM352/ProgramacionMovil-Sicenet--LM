package com.example.appsicenet

import android.app.Application
import com.example.appsicenet.data.AppContainer
import com.example.appsicenet.data.RetrofitClient

class SicenetApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = RetrofitClient(this)
    }
}