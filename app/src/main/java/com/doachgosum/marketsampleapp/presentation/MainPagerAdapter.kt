package com.doachgosum.marketsampleapp.presentation

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.doachgosum.marketsampleapp.constant.LogTag

class MainPagerAdapter(
    vararg page: View
): RecyclerView.Adapter<MainPagerAdapter.MainPagerViewHolder>() {

    private val pages: List<View>

    init {
        pages = page.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPagerViewHolder {
        val containerView = FrameLayout(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        return MainPagerViewHolder(containerView)
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun onBindViewHolder(holder: MainPagerViewHolder, position: Int) {
        holder.bind(pageView = pages[position])
        Log.d(LogTag.TAG_DEBUG, "onMainPageBind")
    }

    class MainPagerViewHolder(
        private val containerView: ViewGroup
    ): RecyclerView.ViewHolder(containerView) {

        fun bind(pageView: View) {
            containerView.removeAllViews()
            containerView.addView(pageView)
        }
    }

}