package com.doachgosum.marketsampleapp.domain.model

data class MarketModel(
    val currencyPair: Pair<String, String>,
    val timestamp: Long,
    val price: Double,
    val changePercent: Double,
    val changeValue: Double,
    val volume: Double
)
