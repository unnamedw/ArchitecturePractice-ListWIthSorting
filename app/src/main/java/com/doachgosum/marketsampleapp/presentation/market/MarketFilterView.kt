package com.doachgosum.marketsampleapp.presentation.market

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.doachgosum.marketsampleapp.databinding.LayoutMarketFilterBinding

/**
 * TODO("Filter View 모듈화")
 * **/
class MarketFilterView: ConstraintLayout {

    private val binding: LayoutMarketFilterBinding = LayoutMarketFilterBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    fun setState(state: MarketSortState) {
        binding.state = state
    }

}