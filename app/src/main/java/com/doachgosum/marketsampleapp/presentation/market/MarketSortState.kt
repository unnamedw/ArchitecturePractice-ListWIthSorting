package com.doachgosum.marketsampleapp.presentation.market

data class MarketSortState(
    val name: SortType = SortType.NONE,
    val price: SortType = SortType.NONE,
    val change: SortType = SortType.NONE,
    val volume: SortType = SortType.NONE
)