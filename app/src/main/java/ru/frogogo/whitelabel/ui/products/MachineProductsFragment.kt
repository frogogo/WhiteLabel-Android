package ru.frogogo.whitelabel.ui.products

import androidx.navigation.fragment.navArgs
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.ajalt.timberkt.d
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentMachineProductsBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.ui.products.select.MachineProductSelectionDialogFragment
import ru.frogogo.whitelabel.ui.products.select.MachineProductSelectionDialogFragment.Companion.showIn
import ru.frogogo.whitelabel.util.ItemDecoration
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment.Companion.showIn

class MachineProductsFragment : BaseFragment<MachineProductsViewModel>() {

  override val viewModel: MachineProductsViewModel by viewModel { parametersOf(args.receiptId, args.vendingMachine) }

  private val binding: FragmentMachineProductsBinding by viewBinding()
  private val args: MachineProductsFragmentArgs by navArgs()
  private val adapter: BaseDelegationAdapter by unsafeLazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_machine_products,
    screen = AnalyticsScreen.PRODUCTS
  )

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
      textViewTimerRemaining.text = state.timeRemaining.toString()
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
