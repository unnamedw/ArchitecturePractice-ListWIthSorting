package com.doachgosum.marketsampleapp.presentation.market

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.LayoutMarketListBinding
import com.doachgosum.marketsampleapp.presentation.util.getAppContainer
import com.doachgosum.marketsampleapp.presentation.util.showToast
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketListFragment: Fragment() {
    /**
     * setFragmentResultListener 의 경우 동일한 키의 listener 가 등록되면
     * 내부적으로 기존 키를 가지고 있는 listener를 삭제하기 때문에
     * 마켓, 즐겨찾기 두 fragment에서 모두 결과를 받을 수 없다.
     * 따라서 인스턴스마다 고유의 키 값을 만들어 주어야 함.
     * **/
    val KEY_QUERY = "key_query_${hashCode()}"

    private val listType: ListType by lazy {
        requireArguments().getSerializable(PARAM_LIST_TYPE) as ListType
    }

    private lateinit var binding: LayoutMarketListBinding
    private val viewModel: MarketListViewModel by viewModels {
        MarketListViewModel.Factory(listType, getAppContainer().marketRepository)
    }

    private val marketListAdapter: MarketListAdapter by lazy { MarketListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(KEY_QUERY) { _, bundle ->
            val query = bundle.getString(PARAM_QUERY)
            Log.d(LogTag.TAG_DEBUG, "$KEY_QUERY result >> $query")
            viewModel.setQuery(query)
        }
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
        private const val PARAM_QUERY = "param_query"
        private const val PARAM_LIST_TYPE = "param_list_type"

        fun newInstance(listType: ListType) = MarketListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PARAM_LIST_TYPE, listType)
            }
        }

        fun createQueryBundle(query: String) = bundleOf(PARAM_QUERY to query)
    }

}