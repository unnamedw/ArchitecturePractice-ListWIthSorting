package com.doachgosum.marketsampleapp.data.repository

import com.doachgosum.marketsampleapp.data.local.AppPreferences
import com.doachgosum.marketsampleapp.data.remote.dto.MarketApiModel
import com.doachgosum.marketsampleapp.data.remote.service.MarketService
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MarketRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val marketService: MarketService,
    private val preferences: AppPreferences
): MarketRepository {

    override suspend fun getAllMarket(): Map<String, MarketApiModel> = withContext(ioDispatcher) {
        return@withContext marketService.getAllMarket()
    }

}