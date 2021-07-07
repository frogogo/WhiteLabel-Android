package ru.frogogo.whitelabel.ui.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel
import ru.frogogo.whitelabel.data.model.ui.receipt.ReceiptUiModel
import ru.frogogo.whitelabel.databinding.FragmentHomeBinding
import ru.frogogo.whitelabel.extension.*
import ru.frogogo.whitelabel.ui.common.CommonAdapterDelegates
import ru.frogogo.whitelabel.ui.receipt_info.ReceiptInfoDialogFragment
import ru.frogogo.whitelabel.ui.receipt_info.ReceiptInfoDialogFragment.Companion.showIn
import ru.frogogo.whitelabel.util.ItemDecoration
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy

private const val SPAN_COUNT = 2
private const val SPAN_COUNT_ITEM = 1

class HomeFragment : BaseFragment<HomeViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: HomeViewModel by viewModel()

  private val binding: FragmentHomeBinding by viewBinding()
  private val recycledViewPool: RecyclerView.RecycledViewPool by inject()
  private val adapter by unsafeLazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_home,
    screen = AnalyticsScreen.HOME
  )

  override fun initViews() {
    initRecyclerView()
    binding.apply {
      buttonProfile.setSafeOnClickListener(viewModel::onProfileClicked)
      swipeRefreshLayout.setOnRefreshListener(viewModel::refreshData)
      viewErrorState.setOnRefreshClickListener(viewModel::refreshData)
      buttonScan.setSafeOnClickListener(viewModel::onScanClicked)
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(dataLive) { data ->
        binding.swipeRefreshLayout.isRefreshing = false
        adapter.items = data
      }
      observe(isLoadingLive) { isLoading ->
        renderState(isLoading = isLoading)
      }
      observe(effectLiveEvent, ::handleEffect)
      observe(scanButtonStateLive, ::handleScanButtonState)
    }
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
      recyclerView.setVisible(!isLoading && !isError)
    }
  }

  private fun initRecyclerView() {
    val decoration = ItemDecoration(
      horizontalOffset = resources.getDimensionPixelOffset(R.dimen.spacing_4),
      horizontalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_2),
      verticalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_2),
      topSpacing = resources.getDimensionPixelSize(R.dimen.spacing_6),
      bottomSpacing = resources.getDimensionPixelSize(R.dimen.spacing_24)
    )
    val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        if (!adapter.items.indices.contains(position)) return SPAN_COUNT

        return when (adapter.items[position]) {
          is ItemUiModel -> SPAN_COUNT_ITEM
          else -> SPAN_COUNT
        }
      }
    }

    binding.recyclerView.apply {
      setRecycledViewPool(this@HomeFragment.recycledViewPool)
      this.layoutManager = layoutManager
      adapter = this@HomeFragment.adapter
      addItemDecoration(decoration)
      addItemDecoration(HomeOffsetDecoration(requireContext()))
    }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    HomeAdapterDelegates.emptyStateDelegate(),
    HomeAdapterDelegates.sectionHeaderDelegate(),
    HomeAdapterDelegates.couponProgressDelegate(),
    HomeAdapterDelegates.scanUnavailableDelegate(),
    HomeAdapterDelegates.itemsButtonDelegate { viewModel.onItemButtonClicked(it) },
    HomeAdapterDelegates.receiptDelegate { viewModel.onReceiptClicked(it) },
    CommonAdapterDelegates.couponDelegate { viewModel.onCouponClicked(it) },
    CommonAdapterDelegates.itemDelegate(),
  )

  private fun handleEffect(effect: HomeEffect) {
    @Exhaustive
    when (effect) {
      HomeEffect.ShowLoadingError -> showLoadingError()
      is HomeEffect.OpenReceiptInfoDialog -> showReceiptInfoDialog(effect.receipt)
    }
  }

  private fun handleScanButtonState(state: HomeScanButtonState) {
    binding.buttonScan.apply {
      if (state != HomeScanButtonState.HIDDEN) {
        animateToVisible()
      } else {
        animateToGone()
      }
      isEnabled = state == HomeScanButtonState.SHOWN_ENABLED
    }
  }

  private fun showLoadingError() {
    binding.swipeRefreshLayout.isRefreshing = false
    renderState(isError = true)
  }

  private fun showReceiptInfoDialog(receipt: ReceiptUiModel) {
    ReceiptInfoDialogFragment.newInstance(receipt)
      .showIn(childFragmentManager)
  }
}
