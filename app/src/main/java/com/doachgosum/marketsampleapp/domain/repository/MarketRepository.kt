package com.doachgosum.marketsampleapp.domain.repository

import com.doachgosum.marketsampleapp.domain.model.MarketModel

interface MarketRepository {

    suspend fun getAllMarket(): List<MarketModel>

}