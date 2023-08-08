package com.doachgosum.marketsampleapp.data.repository

import com.doachgosum.marketsampleapp.data.local.MarketDao
import com.doachgosum.marketsampleapp.data.remote.dto.toDomainModel
import com.doachgosum.marketsampleapp.data.remote.service.MarketService
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.model.MarketModelWithFavorite
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MarketRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val marketService: MarketService,
    private val marketDao: MarketDao
): MarketRepository {

    private var cachedMarketData: MutableStateFlow<List<MarketModel>> = MutableStateFlow(emptyList())

    override suspend fun fetchAllMarket(onError: (Throwable) -> Unit) {
        withContext(ioDispatcher) {
            kotlin.runCatching {
                marketService.getAllMarket()
                    .map {
                        val currencyPair = it.key
                        val marketApiModel = it.value

                        marketApiModel.toDomainModel(currencyPair)
                    }
                    .also {
                        cachedMarketData.value = it
                    }
            }

        }
    }

    override suspend fun getAllMarket(): List<MarketModel> = withContext(ioDispatcher) {
        return@withContext cachedMarketData.value
    }

    override fun observeAllMarket(): Flow<List<MarketModelWithFavorite>> {
        return cachedMarketData
            .combine(marketDao.getFavoriteMarketFlow()) { markets, favorites ->
                markets.map {
                    MarketModelWithFavorite(
                        marketModel = it,
                        favorite = favorites.contains(it.currencyPair)
                    )
                }
            }.distinctUntilChanged()
    }

    override suspend fun saveFavoriteMarket(currencyPair: Pair<String, String>) = withContext(ioDispatcher) {
        return@withContext marketDao.saveFavoriteMarket(currencyPair)
    }

    override suspend fun deleteFavoriteMarket(currencyPair: Pair<String, String>) {
        marketDao.deleteFavoriteMarket(currencyPair)
    }

    override suspend fun getFavoriteMarket(): List<MarketModel> = withContext(ioDispatcher) {
        return@withContext getAllMarket()
            .filter { marketDao.getFavoriteMarket().contains(it.currencyPair) }
    }

    override fun observeFavoriteMarket(): Flow<List<MarketModelWithFavorite>> {
        return cachedMarketData
            .combine(marketDao.getFavoriteMarketFlow()) { all, favorite ->
                all.filter { favorite.contains(it.currencyPair) }
                    .map { MarketModelWithFavorite(marketModel = it, favorite = true) }
            }.distinctUntilChanged()
    }

}
