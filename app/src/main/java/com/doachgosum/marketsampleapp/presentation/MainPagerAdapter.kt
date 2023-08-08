package com.doachgosum.marketsampleapp.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    vararg page: Fragment
): FragmentStateAdapter(fm, lifecycle) {

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