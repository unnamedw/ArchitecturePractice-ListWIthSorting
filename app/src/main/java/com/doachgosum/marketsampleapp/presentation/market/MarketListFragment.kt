package com.doachgosum.marketsampleapp.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.doachgosum.marketsampleapp.databinding.LayoutMarketListBinding
import com.doachgosum.marketsampleapp.presentation.util.getAppContainer
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketListFragment: Fragment() {
    private val listType: ListType by lazy {
        requireArguments().getSerializable(PARAM_LIST_TYPE) as ListType
    }

    private lateinit var binding: LayoutMarketListBinding
    private val viewModel: MarketListViewModel by viewModels {
        MarketListViewModel.Factory(listType, getAppContainer().marketRepository)
    }

    private val marketListAdapter: MarketListAdapter by lazy { MarketListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutMarketListBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }

        subscribeViewModel()
        initView()

        return binding.root
    }

    private fun subscribeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.event
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { event ->
                    when (event) {
                        is MarketListPageEvent.ShowUndoSnackBar -> {
                            Snackbar.make(binding.root, event.msg, Snackbar.LENGTH_LONG)
                            .setAction("추가") {
                                viewModel.saveFavoriteMarket(event.market.currencyPair)
                            }
                            .show()
                        }
                    }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.marketListItems
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    marketListAdapter.submitList(it)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sortState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    binding.frameMarketFilter.state = it
                }
        }
    }

    private fun initView() {
        binding.rvMarketList.adapter = marketListAdapter
        binding.frameMarketFilter.apply {
            frameFilterName.setOnClickListener { viewModel.clickFilter(MarketSortState.FilterType.NAME) }
            frameFilterPrice.setOnClickListener { viewModel.clickFilter(MarketSortState.FilterType.PRICE) }
            frameFilterChange.setOnClickListener { viewModel.clickFilter(MarketSortState.FilterType.CHANGE) }
            frameFilterVolume.setOnClickListener { viewModel.clickFilter(MarketSortState.FilterType.VOLUME) }
        }
    }

    companion object {
        private const val PARAM_LIST_TYPE = "param_list_type"
        fun newInstance(listType: ListType) = MarketListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_LIST_TYPE, listType)
            }
        }
    }

}