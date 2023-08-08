package com.doachgosum.marketsampleapp.presentation.market

import com.doachgosum.marketsampleapp.domain.model.MarketModel

sealed class MarketListPageEvent {
    data class ShowUndoSnackBar(val market: MarketModel, val msg: String): MarketListPageEvent()
}