package ru.poprobuy.poprobuy.ui.profile.receipts

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.BaseAdapterDelegates
import ru.poprobuy.poprobuy.arch.recycler.BaseDelegationAdapter
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.data.model.ui.ReceiptsEmptyState
import ru.poprobuy.poprobuy.databinding.FragmentReceiptsBinding
import ru.poprobuy.poprobuy.extension.observe
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.util.ItemDecoration

class ReceiptsFragment : BaseFragment<ReceiptsViewModel>(R.layout.fragment_receipts) {

  override val viewModel: ReceiptsViewModel by viewModel()

  private val binding: FragmentReceiptsBinding by viewBinding()
  private val adapter: BaseDelegationAdapter by lazy { createAdapter() }

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
    viewModel.run {
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
    BaseAdapterDelegates.emptyListDelegate(),
    ReceiptsAdapterDelegates.receiptDelegate(viewModel::navigateToReceipt),
    ReceiptsAdapterDelegates.scanAvailableDelegate(),
    emptyListItem = ReceiptsEmptyState
  )

}
