package com.doachgosum.marketsampleapp.domain.repository

import com.doachgosum.marketsampleapp.data.remote.dto.MarketApiModel

interface MarketRepository {

    suspend fun getAllMarket(): Map<String, MarketApiModel>

}