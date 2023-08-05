package com.doachgosum.marketsampleapp.data.remote.service

import com.doachgosum.marketsampleapp.data.remote.dto.MarketApiModel
import retrofit2.http.GET

interface MarketService {

    @GET("ticker/detailed/all")
    suspend fun getAllMarket(): Map<String, MarketApiModel>

}