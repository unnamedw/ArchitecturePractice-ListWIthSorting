package com.doachgosum.marketsampleapp.presentation.market

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.doachgosum.marketsampleapp.R
import com.doachgosum.marketsampleapp.databinding.LayoutMarketListBinding

class MarketListView: LinearLayoutCompat {

    private val binding: LayoutMarketListBinding = LayoutMarketListBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private val marketListAdapter: MarketListAdapter by lazy { MarketListAdapter() }

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)

    init {
        binding.rvMarketList.adapter = marketListAdapter
    }

    fun updateItem(newItems: List<MarketItemUiState>) {
        marketListAdapter.submitList(newItems)
    }

}