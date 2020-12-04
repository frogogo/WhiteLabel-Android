package ru.poprobuy.poprobuy.ui.products

import androidx.navigation.fragment.navArgs
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.ajalt.timberkt.d
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.recycler.BaseDelegationAdapter
import ru.poprobuy.poprobuy.core.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentMachineProductsBinding
import ru.poprobuy.poprobuy.extension.observe
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.ui.products.select.MachineProductSelectionDialogFragment
import ru.poprobuy.poprobuy.ui.products.select.MachineProductSelectionDialogFragment.Companion.showIn
import ru.poprobuy.poprobuy.util.ItemDecoration
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen
import ru.poprobuy.poprobuy.view.dialog.ErrorDialogFragment
import ru.poprobuy.poprobuy.view.dialog.ErrorDialogFragment.Companion.showIn

class MachineProductsFragment : BaseFragment<MachineProductsViewModel>(
  layoutId = R.layout.fragment_machine_products,
  screen = AnalyticsScreen.PRODUCTS
) {

  override val viewModel: MachineProductsViewModel by viewModel { parametersOf(args.receiptId, args.vendingMachine) }

  private val binding: FragmentMachineProductsBinding by viewBinding()
  private val args: MachineProductsFragmentArgs by navArgs()
  private val adapter: BaseDelegationAdapter by lazy { createAdapter() }

  override fun initViews() {
    binding.buttonClose.setOnSafeClickListener { viewModel.navigateBack() }
    // Recycler View
    val decorationSpacing = resources.getDimensionPixelSize(R.dimen.spacing_4)
    binding.recyclerView.apply {
      adapter = this@MachineProductsFragment.adapter
      addItemDecoration(ItemDecoration(verticalSpacing = decorationSpacing))
      setOnScrollChangeListener { _, _, _, _, _ ->
        binding.toolbar.isSelected = canScrollVertically(-1)
      }
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(dataLive) { adapter.items = it }
      observe(isLoadingLive) { binding.progressBar.setVisible(it) }
      observe(timerStateLive, ::renderTimer)
      observe(commandLive, ::handleCommand)
    }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    ProductsAdapterDelegates.cellDelegate { viewModel.takeProduct(it) }
  )

  private fun renderTimer(state: MachineProductsViewModel.TimerState) {
    binding.layoutTimer.apply {
      textViewTimberRemaining.text = state.timeRemaining.toString()
      viewTimer.progressMax = state.maxProgress.toFloat()
      viewTimer.progress = state.progress
    }
  }

  private fun handleCommand(command: MachineProductsCommand) {
    @Exhaustive
    when (command) {
      is MachineProductsCommand.ShowSelectionDialog -> showProductSelectionDialog(command)
      is MachineProductsCommand.ShowErrorDialog -> showErrorDialog(command)
    }
  }

  private fun showProductSelectionDialog(command: MachineProductsCommand.ShowSelectionDialog) {
    d { "Selecting product $command" }
    MachineProductSelectionDialogFragment.newInstance(
      receiptId = command.receiptId,
      vendingMachineId = command.vendingMachineId,
      vendingCell = command.vendingCell
    ).showIn(childFragmentManager)
  }

  private fun showErrorDialog(command: MachineProductsCommand.ShowErrorDialog) {
    d { "Showing error dialog" }
    ErrorDialogFragment.newInstance(command.error)
      .showIn(childFragmentManager)
  }

}
