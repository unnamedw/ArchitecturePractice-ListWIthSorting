package com.doachgosum.marketsampleapp.presentation.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.doachgosum.marketsampleapp.domain.model.MarketModel
import com.doachgosum.marketsampleapp.domain.repository.MarketRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MarketListViewModel(
    private val listType: ListType,
    private val marketRepository: MarketRepository
): ViewModel() {

    private val _event: MutableSharedFlow<MarketListPageEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _query: MutableStateFlow<String?> = MutableStateFlow(null)

    private val _sortState: MutableStateFlow<MarketSortState> = MutableStateFlow(MarketSortState())
    val sortState = _sortState.asStateFlow()

    private val marketListSource = when (listType) {
        ListType.Main -> marketRepository.observeAllMarket()
        ListType.Favorite -> marketRepository.observeFavoriteMarket()
    }

    val marketListItems: StateFlow<List<MarketItemUiState>> = marketListSource
        .map { list -> list.map { it.toMarketItemUiState(::onFavoriteClick) } }
        // 기본 정렬
        .map { list -> list.sortedByDescending { it.market.volume } }
        .combine(_query) { marketList, query -> marketList.queried(query) }
        .combine(_sortState) { marketList, sort -> marketList.sorted(sort) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    private fun onFavoriteClick(market: MarketModel, toBeFavorite: Boolean) {
        viewModelScope.launch {
            if (toBeFavorite) {
                saveFavoriteMarket(market.currencyPair)
            } else {
                deleteFavoriteMarket(market.currencyPair)
                _event.emit(MarketListPageEvent.ShowUndoSnackBar(market, "해당 마켓을 다시 추가하시겠어요?"))
            }
        }
    }

    fun saveFavoriteMarket(currency: Pair<String, String>) {
        viewModelScope.launch {
            marketRepository.saveFavoriteMarket(currency)
        }
    }

    private fun deleteFavoriteMarket(currency: Pair<String, String>) {
        viewModelScope.launch {
            marketRepository.deleteFavoriteMarket(currency)
        }
    }

    fun clickFilter(filterType: MarketSortState.FilterType) {
        _sortState.value = _sortState.value.let {
            it.copy(
                filterType = filterType,
                sortType = it.sortType.next()
            )
        }
    }

    fun setQuery(query: String?) {
        _query.value = query
    }

    class Factory(
        private val listType: ListType,
        private val marketRepository: MarketRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MarketListViewModel(listType, marketRepository) as T
        }
    }
}

fun List<MarketItemUiState>.queried(query: String?): List<MarketItemUiState> {
    if (query.isNullOrBlank())
        return this

    return filter {
        it.market.currencyPair.first.contains(query, true)
                || it.market.currencyPair.second.contains(query, true)
    }
}

fun List<MarketItemUiState>.sorted(sortState: MarketSortState): List<MarketItemUiState> {
    return when (sortState.filterType) {

        MarketSortState.FilterType.NAME -> {
            when (sortState.sortType) {
                MarketSortState.SortType.ASC -> sortedBy { it.market.currencyPair.first }
                    .sortedBy { it.market.currencyPair.second }
                MarketSortState.SortType.DESC -> sortedByDescending { it.market.currencyPair.first }
                    .sortedByDescending { it.market.currencyPair.second }
                MarketSortState.SortType.NONE -> this
            }
        }

        MarketSortState.FilterType.PRICE -> {
            when (sortState.sortType) {
                MarketSortState.SortType.ASC -> sortedBy { it.market.price }
                MarketSortState.SortType.DESC -> sortedByDescending { it.market.price }
                MarketSortState.SortType.NONE -> this
            }
        }

        MarketSortState.FilterType.CHANGE -> {
            when (sortState.sortType) {
                MarketSortState.SortType.ASC -> sortedBy { it.market.changePercent }
                MarketSortState.SortType.DESC -> sortedByDescending { it.market.changePercent }
                MarketSortState.SortType.NONE -> this
            }
        }

        MarketSortState.FilterType.VOLUME -> {
            when (sortState.sortType) {
                MarketSortState.SortType.ASC -> sortedBy { it.market.volume }
                MarketSortState.SortType.DESC -> sortedByDescending { it.market.volume }
                MarketSortState.SortType.NONE -> this
            }
        }

        MarketSortState.FilterType.NONE -> this
    }
}