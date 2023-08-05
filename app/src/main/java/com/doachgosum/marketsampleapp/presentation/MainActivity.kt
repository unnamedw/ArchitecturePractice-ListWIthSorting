package com.doachgosum.marketsampleapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.ActivityMainBinding
import com.doachgosum.marketsampleapp.presentation.market.MarketListView
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

    private val homeMarketListView: MarketListView by lazy { MarketListView(this) }
    private val favoriteMarketListView: MarketListView by lazy { MarketListView(this) }

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
                    homeMarketListView.updateItem(it)
                }
        }
    }

    private fun initView() {
        binding.vpContents.adapter = MainPagerAdapter(homeMarketListView, favoriteMarketListView)

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