package com.doachgosum.marketsampleapp.di

import android.content.Context
import com.doachgosum.marketsampleapp.data.local.AppDatabase
import com.doachgosum.marketsampleapp.data.local.AppPreferences
import com.doachgosum.marketsampleapp.data.remote.service.MarketService
import com.doachgosum.marketsampleapp.data.repository.MarketRepositoryImpl
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppContainer(
    private val context: Context
) {

    private val ioDispatcher: CoroutineDispatcher by lazy { Dispatchers.IO }

    /** Local cache **/
    private val db: AppDatabase by lazy { DatabaseModule.providesAppDatabase(context) }
    private val prefs: AppPreferences by lazy { AppPreferences(context) }

    /** API Service **/
    private val marketService: MarketService by lazy {
        RetrofitModule.createService(MarketService::class.java)
    }

    /** Repository **/
    val marketRepository: MarketRepository by lazy {
        MarketRepositoryImpl(
            ioDispatcher = ioDispatcher,
            marketService = marketService,
            marketDao = prefs
        )
    }

    /** UseCase **/
}