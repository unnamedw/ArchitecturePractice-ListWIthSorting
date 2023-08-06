package com.doachgosum.marketsampleapp.domain.repository

import com.doachgosum.marketsampleapp.domain.model.MarketModel
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

    suspend fun getAllMarket(): List<MarketModel>

    suspend fun saveFavoriteMarket(currencyPair: Pair<String, String>)

    suspend fun deleteFavoriteMarket(currencyPair: Pair<String, String>)

    /**
     * return -> Market 의 Currency Pair List
     * **/
    suspend fun getFavoriteMarket(): List<Pair<String, String>>

    fun getFavoriteMarketFlow(): Flow<List<Pair<String, String>>>

}