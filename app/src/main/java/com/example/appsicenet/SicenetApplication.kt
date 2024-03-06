package com.example.appsicenet

import android.app.Application
import androidx.work.WorkManager
import com.example.appsicenet.data.AppContainer
import com.example.appsicenet.data.DefaultAppContainer

class SicenetApplication: Application() {

    lateinit var container: AppContainer
    lateinit var workManager: WorkManager
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        workManager = WorkManager.getInstance(this)
    }
}