package ru.frogogo.whitelabel.ui.profile.receipts

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentReceiptsBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.util.ItemDecoration
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy

class ReceiptsFragment : BaseFragment<ReceiptsViewModel>() {

  override val viewModel: ReceiptsViewModel by viewModel()

  private val binding: FragmentReceiptsBinding by viewBinding()
  private val adapter: BaseDelegationAdapter by unsafeLazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_receipts,
    screen = AnalyticsScreen.RECEIPTS
  )

  override fun initViews() {
    binding.apply {
      buttonBack.setOnSafeClickListener(viewModel::navigateBack)
      viewErrorState.setOnRefreshClickListener(viewModel::loadReceipts)
    }

    // Recycler View
    val decorationSpacing = resources.getDimensionPixelSize(R.dimen.spacing_4)
    binding.recyclerView.apply {
      adapter = this@ReceiptsFragment.adapter
      addItemDecoration(ItemDecoration(verticalSpacing = decorationSpacing))
      setOnScrollChangeListener { _, _, _, _, _ ->
        binding.layoutToolbar.isSelected = canScrollVertically(-1)
      }
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(dataLive) { adapter.items = it }
      observe(isLoadingLive) { renderState(isLoading = it) }
      observe(errorOccurredLiveEvent) { renderState(isError = true) }
    }
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
    }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    ReceiptsAdapterDelegates.receiptEmptyState(),
    ReceiptsAdapterDelegates.receiptDelegate(viewModel::navigateToReceipt),
    ReceiptsAdapterDelegates.scanAvailableDelegate()
  )
}
