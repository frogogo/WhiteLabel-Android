package ru.poprobuy.poprobuy.ui.products

import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.ajalt.timberkt.d
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.BaseDelegationAdapter
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.data.model.ui.product.ProductUiModel
import ru.poprobuy.poprobuy.databinding.FragmentProductsBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.ui.products.select.ProductSelectionCommand
import ru.poprobuy.poprobuy.ui.products.select.ProductSelectionDialogFragment
import ru.poprobuy.poprobuy.ui.products.select.ProductSelectionInteractor
import ru.poprobuy.poprobuy.util.ItemDecoration

class ProductsFragment : BaseFragment<ProductsViewModel>(R.layout.fragment_products) {

  override val viewModel: ProductsViewModel by viewModel()

  private val selectionInteractor: ProductSelectionInteractor by sharedViewModel()
  private val binding: FragmentProductsBinding by viewBinding()
  private val adapter: BaseDelegationAdapter by lazy { createAdapter() }

  override fun initViews() {
    binding.buttonClose.setOnSafeClickListener { viewModel.navigateBack() }
    // Recycler View
    val decorationSpacing = resources.getDimensionPixelSize(R.dimen.spacing_4)
    binding.recyclerView.apply {
      adapter = this@ProductsFragment.adapter
      addItemDecoration(ItemDecoration(verticalSpacing = decorationSpacing, horizontalSpacing = 0))
    }
  }

  override fun initObservers() = viewModel.run {
    dataLive.observe { adapter.items = it }
    isLoadingLive.observe { binding.progressBar.setVisible(it) }
    timerStateLive.observe { renderTimer(it) }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    ProductsAdapterDelegates.productDelegate(this::showProductSelectionDialog)
  )

  private fun renderTimer(state: ProductsViewModel.TimerState) {
    binding.layoutTimer.apply {
      textViewTimberRemaining.text = state.timeRemaining.toString()
      viewTimer.progressMax = state.maxProgress.toFloat()
      viewTimer.progress = state.progress
    }
  }

  private fun showProductSelectionDialog(product: ProductUiModel) {
    d { "Selecting product $product" }
    selectionInteractor.issueCommand(ProductSelectionCommand.SetProduct(product))
    ProductSelectionDialogFragment.newInstance().show(childFragmentManager, null)
  }

}
