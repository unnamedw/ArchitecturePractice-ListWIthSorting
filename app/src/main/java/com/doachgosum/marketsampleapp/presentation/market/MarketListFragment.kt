package com.doachgosum.marketsampleapp.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.doachgosum.marketsampleapp.databinding.LayoutMarketListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketListFragment: Fragment() {

    private lateinit var binding: LayoutMarketListBinding
    private lateinit var viewModel: MarketListViewModel

    private val marketListAdapter: MarketListAdapter by lazy { MarketListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MarketListViewModel.Factory())[MarketListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutMarketListBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }

        subscribeViewModel()
        initView()

        return binding.root
    }

    private fun subscribeViewModel() {

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.sortState
//                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//                .collectLatest {
//                    binding.frameMarketFilter.state = it
//                }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.marketListItems
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    marketListAdapter.submitList(it)
                }
        }
    }

    private fun initView() {
        binding.rvMarketList.adapter = marketListAdapter
    }

    fun updateItem(newItems: List<MarketItemUiState>) {
        viewModel.updateList(newItems)
    }

}