package com.doachgosum.marketsampleapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.ActivityMainBinding
import com.doachgosum.marketsampleapp.presentation.market.MarketListFragment
import com.doachgosum.marketsampleapp.presentation.util.getAppContainer
import com.doachgosum.marketsampleapp.presentation.util.showToast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(getAppContainer().marketRepository)
    }

    private val homeMarketListFragment: MarketListFragment = MarketListFragment()
    private val favoriteMarketListFragment: MarketListFragment = MarketListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeViewModel()
        initView()
    }

    private fun subscribeViewModel() {
        lifecycleScope.launch {
            viewModel.marketList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    Log.d(LogTag.TAG_DEBUG, "data >> $it")
                    homeMarketListFragment.updateItem(it)
                }
        }

        lifecycleScope.launch {
            viewModel.favoriteList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    favoriteMarketListFragment.updateItem(it)
                }
        }
    }

    private fun initView() {
        binding.vpContents.adapter = MainPagerAdapter(this, homeMarketListFragment, favoriteMarketListFragment)

        val tabTitle = listOf("마켓", "즐겨찾기")
        TabLayoutMediator(binding.tabLayout, binding.vpContents) { tab, index ->
            tab.text = tabTitle[index]
        }.attach()
    }

    private var lastBackPressed: Long = 0L
    override fun onBackPressed() {
        val tmp = System.currentTimeMillis()

        if (tmp - lastBackPressed > 2000L) {
            showToast("'뒤로' 버튼을 한번 더 누르면 종료됩니다")
        } else {
            onBackPressedDispatcher.onBackPressed()
        }

        lastBackPressed = tmp
    }
}