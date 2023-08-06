package com.doachgosum.marketsampleapp.presentation.market

import com.doachgosum.marketsampleapp.R
import com.doachgosum.marketsampleapp.domain.model.MarketModel

data class MarketItemUiState(
    val market: MarketModel,
    val isFavorite: Boolean = false,
    val onFavoriteClick: (market: MarketModel, toBe: Boolean) -> Unit
) {

    val signTextColorResId: Int = when {
        market.changePercent > 0 -> R.color.red_500
        market.changePercent < 0 -> R.color.blue_600
        else -> R.color.gray_900
    }

}
