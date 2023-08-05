package com.doachgosum.marketsampleapp

import android.app.Application
import com.doachgosum.marketsampleapp.di.AppContainer

class MyApplication: Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}