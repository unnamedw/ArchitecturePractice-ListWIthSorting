package com.doachgosum.marketsampleapp.presentation.market

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doachgosum.marketsampleapp.R
import com.doachgosum.marketsampleapp.databinding.ViewHolderMarketItemBinding
import com.doachgosum.marketsampleapp.domain.util.toMarketFormat
import com.doachgosum.marketsampleapp.domain.util.toMarketVolumeFormat

class MarketItemViewHolder(
    private val binding: ViewHolderMarketItemBinding
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: MarketItemUiState) {
        binding.frameFavorite.setOnClickListener {
            item.onFavoriteClick.invoke(item.market, !item.isFavorite)
        }

        val favoriteResId = when {
            item.isFavorite -> R.drawable.ic_star_filled
            else -> R.drawable.ic_star_border
        }
        binding.ivFavorite.setImageResource(favoriteResId)

        binding.tvCurrencyPair.text = item.market.currencyPair.let { "${it.first} / ${it.second}" }

        binding.tvPrice.apply {
            text = item.market.price.toMarketFormat()
            setTextColor(ContextCompat.getColor(context, item.signTextColorResId))
        }

        binding.tvChangePercent.apply {
            text = "${item.market.changePercent.toMarketFormat()}%"
            setTextColor(ContextCompat.getColor(context, item.signTextColorResId))
        }

        binding.tvChangeAmount.apply {
            text = item.market.changeValue.toMarketFormat()
            setTextColor(ContextCompat.getColor(context, item.signTextColorResId))
        }

        item.market.volume.toMarketVolumeFormat().also {
            binding.tvVolume.text = it.first

            it.second?.also { unit ->
                binding.tvVolumeUnit.visibility = View.VISIBLE
                binding.tvVolumeUnit.text = unit
            } ?: kotlin.run {
                binding.tvVolumeUnit.visibility = View.INVISIBLE
            }
        }
    }

}