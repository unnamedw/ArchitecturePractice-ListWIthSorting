package com.doachgosum.marketsampleapp.presentation.market

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.ViewHolderMarketItemBinding

class MarketListAdapter: ListAdapter<MarketItemUiState, MarketItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        return MarketItemViewHolder(
            ViewHolderMarketItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        Log.d(LogTag.TAG_DEBUG, "onMarketListItemBind")
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MarketItemUiState>() {
            override fun areItemsTheSame(oldItem: MarketItemUiState, newItem: MarketItemUiState): Boolean {
                return oldItem.market.currencyPair == newItem.market.currencyPair
            }

            override fun areContentsTheSame(oldItem: MarketItemUiState, newItem: MarketItemUiState): Boolean {
                return oldItem == newItem
            }

        }
    }
}