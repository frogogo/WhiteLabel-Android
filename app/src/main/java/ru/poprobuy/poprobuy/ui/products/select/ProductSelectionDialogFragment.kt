package ru.poprobuy.poprobuy.ui.products.select

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseDialogFragment
import ru.poprobuy.poprobuy.databinding.FragmentProductSelectionBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

class ProductSelectionDialogFragment : BaseDialogFragment(R.layout.fragment_product_selection) {

  private val viewModel: ProductSelectionViewModel by viewModel()
  private val interactor: ProductSelectionInteractor by sharedViewModel()
  private val binding: FragmentProductSelectionBinding by viewBinding {
    FragmentProductSelectionBinding.bind(requireView())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    isCancelable = false
  }

  override fun initViews() {
    binding.apply {
      layoutProduct.apply {
        buttonYes.setOnSafeClickListener { viewModel.confirmSelection() }
        buttonNo.setOnSafeClickListener { dismiss() }
      }
      layoutSuccess.buttonDone.setOnSafeClickListener { dismiss() }
      layoutError.buttonOk.setOnSafeClickListener { dismiss() }
    }
  }

  override fun initObservers() {
    viewModel.apply {
      stateLive.observe(viewLifecycleOwner) { renderState(it) }
    }
    interactor.getCommandEvent().observe(viewLifecycleOwner) { event ->
      when (event) {
        is ProductSelectionCommand.SetProduct -> viewModel.setProduct(event.product)
      }
    }
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

  companion object {
    fun newInstance(): ProductSelectionDialogFragment = ProductSelectionDialogFragment()
  }

}
