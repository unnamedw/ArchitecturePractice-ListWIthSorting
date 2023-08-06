package com.doachgosum.marketsampleapp.presentation.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarketListViewModel: ViewModel() {

    private val _marketListItems: MutableStateFlow<List<MarketItemUiState>> = MutableStateFlow(emptyList())
    val marketListItems = _marketListItems.asStateFlow()

    private val _sortState: MutableStateFlow<MarketSortState> = MutableStateFlow(MarketSortState(volume = SortType.DESC))
    val sortState = _sortState.asStateFlow()

    fun updateList(newItems: List<MarketItemUiState>) {
        viewModelScope.launch {
            _marketListItems.value = newItems
        }
    }

    fun clickSortByName() {}

    fun clickSortByPrice() {}

    fun clickSortByChange() {}

    fun clickSortByVolume() {}

    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MarketListViewModel() as T
        }
    }
}