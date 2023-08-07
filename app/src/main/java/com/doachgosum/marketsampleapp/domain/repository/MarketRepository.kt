package com.doachgosum.marketsampleapp.domain.repository

import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.model.MarketModelWithFavorite
import kotlinx.coroutines.flow.Flow

interface MarketRepository {

    suspend fun fetchAllMarket(onError: (Throwable) -> Unit)

    suspend fun getAllMarket(): List<MarketModel>

    fun observeAllMarket(): Flow<List<MarketModelWithFavorite>>

    suspend fun saveFavoriteMarket(currencyPair: Pair<String, String>)

    suspend fun deleteFavoriteMarket(currencyPair: Pair<String, String>)

    /**
     * return -> Market 의 Currency Pair List
     * **/
    suspend fun getFavoriteMarket(): List<MarketModel>

    fun observeFavoriteMarket(): Flow<List<MarketModelWithFavorite>>

}