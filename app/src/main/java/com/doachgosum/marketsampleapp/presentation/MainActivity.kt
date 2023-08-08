package com.doachgosum.marketsampleapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.ActivityMainBinding
import com.doachgosum.marketsampleapp.presentation.market.ListType
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

    private val homeMarketListFragment: MarketListFragment by lazy { MarketListFragment.newInstance(ListType.Main) }
    private val favoriteMarketListFragment: MarketListFragment by lazy { MarketListFragment.newInstance(ListType.Favorite) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeViewModel()
        initView()

        viewModel.fetchMarketData()
    }

    private fun subscribeViewModel() {
        lifecycleScope.launch {
            viewModel.commonEvent
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { event ->
                    when (event) {
                        is CommonEvent.ShowToast -> showToast(event.msg)
                    }
                }
        }
    }

    private fun initView() {
        setUpViewPager()

        binding.etSearch.doAfterTextChanged {
            supportFragmentManager.setFragmentResult(
                homeMarketListFragment.KEY_QUERY,
                MarketListFragment.createQueryBundle(it.toString())
            )
            supportFragmentManager.setFragmentResult(
                favoriteMarketListFragment.KEY_QUERY,
                MarketListFragment.createQueryBundle(it.toString())
            )
        }
    }

    private fun setUpViewPager() {
        binding.vpContents.adapter = MainPagerAdapter(
            supportFragmentManager,
            lifecycle,
            homeMarketListFragment, favoriteMarketListFragment
        )

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