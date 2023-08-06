package com.doachgosum.marketsampleapp.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(
    fa: FragmentActivity,
    vararg page: Fragment
): FragmentStateAdapter(fa) {

    private val pages: List<Fragment>

    init {
        pages = page.toList()
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }


}