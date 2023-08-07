package com.doachgosum.marketsampleapp.presentation.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MarketListViewModel(
    private val listType: ListType,
    private val marketRepository: MarketRepository
): ViewModel() {

    val marketListItems: StateFlow<List<MarketItemUiState>> = when (listType) {
        ListType.Main -> marketRepository.observeAllMarket()
        ListType.Favorite -> marketRepository.observeFavoriteMarket()
    }.map { list ->
        list.map { it.toMarketItemUiState(::onFavoriteClick) }
    }.map { list ->
        // 기본 정렬을 거래량 내림차순 처리
        list.sortedByDescending { it.market.volume }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _sortState: MutableStateFlow<MarketSortState> = MutableStateFlow(MarketSortState())
    val sortState = _sortState.asStateFlow()

    private fun onFavoriteClick(market: MarketModel, toBeFavorite: Boolean) {
        viewModelScope.launch {
            if (toBeFavorite) {
                marketRepository.saveFavoriteMarket(market.currencyPair)
            } else {
                marketRepository.deleteFavoriteMarket(market.currencyPair)
            }
        }
    }

    fun clickSortByName() {}

    fun clickSortByPrice() {}

    fun clickSortByChange() {}

    fun clickSortByVolume() {}

    class Factory(
        private val listType: ListType,
        private val marketRepository: MarketRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MarketListViewModel(listType, marketRepository) as T
        }
    }
}