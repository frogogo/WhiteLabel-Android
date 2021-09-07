package ru.frogogo.whitelabel.ui.item_info

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.annotation.StringRes
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.navigation.fragment.navArgs
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.data.model.ui.item.ItemInfoUiModel
import ru.frogogo.whitelabel.databinding.FragmentItemInfoBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.extension.unloadBindingModuleOnClose
import ru.frogogo.whitelabel.util.PriceUtils
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

private typealias Binding = FragmentItemInfoBinding

class ItemInfoFragment : BaseFragment<ItemInfoViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: ItemInfoViewModel by viewModel { parametersOf(args.itemId) }

  private val binding: Binding by viewBinding(Binding::bind)
  private val args: ItemInfoFragmentArgs by navArgs()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_item_info,
    screen = AnalyticsScreen.ITEM_INFO,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    scope.unloadBindingModuleOnClose()
  }

  override fun initViews() {
    super.initViews()
    binding.apply {
      viewErrorState.setOnRefreshClickListener(viewModel::retry)
      buttonClose.setSafeOnClickListener(viewModel::onBackButtonClicked)
    }
  }

  override fun initObservers() {
    super.initObservers()
    viewModel.apply {
      observe(dataLive, ::renderData)
      observe(isLoadingLive) { isLoading ->
        renderState(isLoading = isLoading)
      }
      observe(effectLiveEvent, ::handleEffect)
    }
  }

  private fun renderData(item: ItemInfoUiModel) {
    binding.layoutContent.apply {
      root.setVisible(true)
      imageViewItem.load(item.imageUrl)
      layoutPrice.apply {
        textViewPriceDiscounted.text = PriceUtils.formatPrice(item.discountedPrice)
        textViewPrice.text = PriceUtils.formatPrice(item.price)
      }
      textViewName.text = formatName(item.name, item.specs)
      textViewDescription.text = item.description
      textViewSpecifications.text = createSpecifications(item)
    }
  }

  private fun handleEffect(effect: ItemInfoEffect) {
    @Exhaustive
    when (effect) {
      ItemInfoEffect.ShowLoadingError -> renderState(isError = true)
    }
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
    }
  }

  private fun formatName(name: String, specs: String?) = buildSpannedString {
    append(name)

    specs?.let { value ->
      inSpans(
        ForegroundColorSpan(requireContext().getColor(R.color.gray_700)),
        TextAppearanceSpan(requireContext(), R.style.Text_Title2),
      ) {
        append(" ")
        append(value)
      }
    }
  }

  private fun createSpecifications(item: ItemInfoUiModel) = buildSpannedString {
    addSpecification(R.string.item__specification_parameter, item.specs)
    addSpecification(R.string.item__specification_full_price, PriceUtils.formatPrice(item.price))
    addSpecification(R.string.item__specification_discounted_price, PriceUtils.formatPrice(item.discountedPrice))
  }

  private fun SpannableStringBuilder.addSpecification(@StringRes nameRes: Int, value: String?) {
    if (value == null) {
      return
    }

    append(getString(nameRes))
    append(" - ")
    color(requireContext().getColor(R.color.black)) {
      append(value)
    }

    appendLine()
  }
}
