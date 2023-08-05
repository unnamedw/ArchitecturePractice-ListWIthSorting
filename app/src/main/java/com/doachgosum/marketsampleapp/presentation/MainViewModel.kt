package com.doachgosum.marketsampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import com.doachgosum.marketsampleapp.presentation.market.MarketItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val marketRepository: MarketRepository
): ViewModel() {

    private val _marketList: MutableStateFlow<List<MarketItemUiState>> = MutableStateFlow(emptyList())
    val marketList = _marketList.asStateFlow()

    init {
        viewModelScope.launch {
            _marketList.value = marketRepository.getAllMarket()
                .map {
                    MarketItemUiState(
                        market = it,
                        onFavoriteClick = ::onFavoriteClick
                    )
                }
        }
    }

    private fun onFavoriteClick(market: MarketModel) {

    }

    class Factory(
        private val marketRepository: MarketRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(marketRepository) as T
        }
    }
}