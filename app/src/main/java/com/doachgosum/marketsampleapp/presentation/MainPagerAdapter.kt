package com.doachgosum.marketsampleapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.LayoutMainPagerBinding

class MainPagerAdapter(
    vararg page: View
): RecyclerView.Adapter<MainPagerAdapter.MainPagerViewHolder>() {

    private val pages: List<View>

    init {
        pages = page.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPagerViewHolder {
        return MainPagerViewHolder(
            LayoutMainPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun onBindViewHolder(holder: MainPagerViewHolder, position: Int) {
        holder.bind(pageView = pages[position])
        Log.d(LogTag.TAG_DEBUG, "onMainPageBind")
    }

    class MainPagerViewHolder(
        private val binding: LayoutMainPagerBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(pageView: View) {
            binding.root.removeAllViews()
            binding.root.addView(pageView)
        }
    }

}