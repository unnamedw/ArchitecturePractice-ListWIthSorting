package com.doachgosum.marketsampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import com.doachgosum.marketsampleapp.presentation.market.MarketItemUiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val marketRepository: MarketRepository
): ViewModel() {

    private val _commonEvent: MutableSharedFlow<CommonEvent> = MutableSharedFlow()
    val commonEvent = _commonEvent.asSharedFlow()

    private val _keywordFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val keywordFlow = _keywordFlow.asStateFlow()

    fun fetchMarketData() {
        viewModelScope.launch {
            marketRepository.fetchAllMarket {
                viewModelScope.launch {
                    _commonEvent.emit(CommonEvent.ShowToast(msg = "데이터를 불러오지 못했어요."))
                }
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