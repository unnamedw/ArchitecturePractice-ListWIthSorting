package com.doachgosum.marketsampleapp.presentation.market

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doachgosum.marketsampleapp.databinding.ViewHolderMarketItemBinding

/**
 * ListAdapter 를 사용하는 경우, 정렬 시 스크롤 위치가 어중간하게 위치하는 현상이 발생함.
 * **/
class MarketListAdapter: RecyclerView.Adapter<MarketItemViewHolder>() {

    private var items: List<MarketItemUiState> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<MarketItemUiState>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        return MarketItemViewHolder(
            ViewHolderMarketItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}