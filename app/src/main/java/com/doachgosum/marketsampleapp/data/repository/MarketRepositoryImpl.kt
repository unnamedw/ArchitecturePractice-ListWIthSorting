package com.doachgosum.marketsampleapp.data.repository

import com.doachgosum.marketsampleapp.data.local.MarketDao
import com.doachgosum.marketsampleapp.data.remote.dto.toDomainModel
import com.doachgosum.marketsampleapp.data.remote.service.MarketService
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MarketRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val marketService: MarketService,
    private val marketDao: MarketDao
): MarketRepository {

    override suspend fun getAllMarket(): List<MarketModel> = withContext(ioDispatcher) {
        return@withContext marketService.getAllMarket()
            .map {
                val currencyPair = it.key
                val marketApiModel = it.value

                marketApiModel.toDomainModel(currencyPair)
            }
    }

    override suspend fun saveFavoriteMarket(currencyPair: Pair<String, String>) = withContext(ioDispatcher) {
        return@withContext marketDao.saveFavoriteMarket(currencyPair)
    }

    override suspend fun getFavoriteMarket(): List<Pair<String, String>> = withContext(ioDispatcher) {
        return@withContext marketDao.getFavoriteMarket()
    }

    override fun getFavoriteMarketFlow(): Flow<List<Pair<String, String>>> {
        return marketDao.getFavoriteMarketFlow()
    }

}