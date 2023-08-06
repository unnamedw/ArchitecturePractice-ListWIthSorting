package com.doachgosum.marketsampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import com.doachgosum.marketsampleapp.presentation.market.MarketItemUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val marketRepository: MarketRepository
): ViewModel() {

    private val _keywordFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val keywordFlow = _keywordFlow.asStateFlow()

    private val _marketList: MutableStateFlow<List<MarketItemUiState>> = MutableStateFlow(emptyList())
    val marketList = _marketList.asStateFlow()

    val favoriteList: StateFlow<List<MarketItemUiState>> = combine(
        _keywordFlow, _marketList, marketRepository.getFavoriteMarketFlow()
    ) { keyword, all, favorites ->

        all.filter { favorites.contains(it.market.currencyPair) }
            .map { it.copy(isFavorite = favorites.contains(it.market.currencyPair)) }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            _marketList.value = marketRepository.getAllMarket()
                .map {
                    MarketItemUiState(
                        market = it,
                        onFavoriteClick = { market, toBe ->  onFavoriteClick(market, toBe) }
                    )
                }
        }
    }

    private fun onFavoriteClick(market: MarketModel, toBeFavorite: Boolean) {
        viewModelScope.launch {
            if (toBeFavorite) {
                marketRepository.saveFavoriteMarket(market.currencyPair)
            } else {
                marketRepository.deleteFavoriteMarket(market.currencyPair)
            }
        }
    }

    private fun enterSearchKeyword(keyword: String) {

    }

    class Factory(
        private val marketRepository: MarketRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(marketRepository) as T
        }
    }
}