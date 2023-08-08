package com.doachgosum.marketsampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val marketRepository: MarketRepository
): ViewModel() {

    private val _commonEvent: MutableSharedFlow<CommonEvent> = MutableSharedFlow()
    val commonEvent = _commonEvent.asSharedFlow()

    fun fetchMarketData() {
        viewModelScope.launch {
            marketRepository.fetchAllMarket(::handleFetchError)
        }
    }

    private fun handleFetchError(throwable: Throwable) {
        viewModelScope.launch {
            throwable.printStackTrace()
            _commonEvent.emit(CommonEvent.ShowToast(msg = "데이터를 불러오지 못했어요."))
        }
    }

    class Factory(
        private val marketRepository: MarketRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(marketRepository) as T
        }
    }
}