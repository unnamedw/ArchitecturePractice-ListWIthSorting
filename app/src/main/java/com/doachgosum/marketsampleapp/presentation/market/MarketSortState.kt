package com.doachgosum.marketsampleapp.presentation.market

import com.doachgosum.marketsampleapp.R

data class MarketSortState(
    val filterType: FilterType = FilterType.NONE,
    val sortType: SortType = SortType.NONE
) {

    enum class FilterType {
        NAME,
        PRICE,
        CHANGE,
        VOLUME,
        NONE
    }

    enum class SortType(val iconResId: Int) {
        ASC(R.drawable.ic_ticker_sort_asc),
        DESC(R.drawable.ic_ticker_sort_desc),
        NONE(R.drawable.ic_ticker_sort_none);

        fun next(): SortType {
            return when (this) {
                NONE -> DESC
                DESC -> ASC
                ASC -> NONE
            }
        }
    }
}