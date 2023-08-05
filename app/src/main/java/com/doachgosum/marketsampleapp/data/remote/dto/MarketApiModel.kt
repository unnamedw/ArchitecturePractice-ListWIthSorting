package com.doachgosum.marketsampleapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.doachgosum.marketsampleapp.domain.model.MarketModel

@Keep
data class MarketApiModel(
    @SerializedName("ask") val ask: String?,
    @SerializedName("bid") val bid: String?,
    @SerializedName("change") val change: String?,
    @SerializedName("changePercent") val changePercent: String?,
    @SerializedName("high") val high: String?,
    @SerializedName("last") val last: String?,
    @SerializedName("low") val low: String?,
    @SerializedName("open") val `open`: String?,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("volume") val volume: String?
)

fun MarketApiModel.toDomainModel(currencyPair: String): MarketModel {
    return MarketModel(
        currencyPair = currencyPair.split("_").let { it.first().uppercase() to it.last().uppercase() },
        timestamp = timestamp,
        price = last?.toDoubleOrNull() ?: 0.0,
        changePercent = changePercent?.toDoubleOrNull() ?: 0.0,
        changeValue = change?.toDoubleOrNull() ?: 0.0,
        volume = volume?.toDoubleOrNull() ?: 0.0
    )
}