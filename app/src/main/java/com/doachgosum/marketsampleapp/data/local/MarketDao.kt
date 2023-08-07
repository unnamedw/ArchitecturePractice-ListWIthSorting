package com.doachgosum.marketsampleapp.data.local

import kotlinx.coroutines.flow.Flow

interface MarketDao {

    fun getFavoriteMarket(): List<Pair<String, String>>

    suspend fun saveFavoriteMarket(currencyPair: Pair<String, String>)
    
    suspend fun deleteFavoriteMarket(currencyPair: Pair<String, String>)

    fun getFavoriteMarketFlow(): Flow<List<Pair<String, String>>>

}