package ru.poprobuy.poprobuy.ui.products.select

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseDialogFragment
import ru.poprobuy.poprobuy.databinding.DialogProductSelectionBinding
import ru.poprobuy.poprobuy.extension.observe
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

class ProductSelectionDialogFragment : BaseDialogFragment(R.layout.dialog_product_selection) {

  private val viewModel: ProductSelectionViewModel by viewModel()
  private val interactor: ProductSelectionInteractor by sharedViewModel()
  private val binding: DialogProductSelectionBinding by viewBinding {
    DialogProductSelectionBinding.bind(requireView())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    isCancelable = false
  }

  override fun initViews() {
    binding.apply {
      layoutProduct.apply {
        buttonYes.setOnSafeClickListener(viewModel::confirmSelection)
        buttonNo.setOnSafeClickListener { dismiss() }
      }
      layoutSuccess.buttonDone.setOnSafeClickListener { dismiss() }
      layoutError.buttonOk.setOnSafeClickListener { dismiss() }
    }
  }

  override fun initObservers() {
    observe(viewModel.stateLive, this::renderState)
    observe(interactor.getCommandEvent(), this::handleCommand)
  }

  private fun renderState(state: ProductSelectionState) {
    binding.apply {
      layoutProduct.root.setVisible(state is ProductSelectionState.Product)
      layoutSuccess.root.setVisible(state is ProductSelectionState.Success)
      layoutError.root.setVisible(state is ProductSelectionState.Error)
    }

    when (state) {
      is ProductSelectionState.Product -> {
        binding.layoutProduct.apply {
          imageViewIcon.load(state.product.imageUrl) {
            placeholder(R.drawable.ic_placeholder)
          }

          textViewTitle.text = if (!state.isLoading) {
            getString(R.string.product_select_title_confirmation, state.product.name)
          } else {
            getString(R.string.product_select_title_goods_issue)
          }
          buttonYes.setVisible(!state.isLoading)
          buttonNo.setVisible(!state.isLoading)
          progressBar.setVisible(state.isLoading)
        }
      }
    }

  }

  private fun handleCommand(command: ProductSelectionCommand) = when (command) {
    is ProductSelectionCommand.SetProduct -> viewModel.setProduct(command.product)
  }

  companion object {

    const val TAG = "ProductSelectionDialogFragment"

    fun newInstance(): ProductSelectionDialogFragment = ProductSelectionDialogFragment()
  }

}
