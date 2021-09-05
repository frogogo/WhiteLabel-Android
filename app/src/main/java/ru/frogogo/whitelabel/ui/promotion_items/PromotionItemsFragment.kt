package ru.frogogo.whitelabel.ui.promotion_items

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.recycler.RecyclerViewItem
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel
import ru.frogogo.whitelabel.databinding.FragmentPromotionItemsBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.extension.unloadBindingModuleOnClose
import ru.frogogo.whitelabel.ui.common.CommonAdapterDelegates
import ru.frogogo.whitelabel.ui.home.HomeAdapterDelegates
import ru.frogogo.whitelabel.ui.home.HomeOffsetDecoration
import ru.frogogo.whitelabel.ui.promotion_items.adapter.PromotionItemsAdapterDelegates
import ru.frogogo.whitelabel.util.ItemDecoration
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy

private typealias Binding = FragmentPromotionItemsBinding

private const val SPAN_COUNT = 2
private const val SPAN_COUNT_ITEM = 1

class PromotionItemsFragment : BaseFragment<PromotionItemsViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: PromotionItemsViewModel by viewModel { parametersOf(args.promotion) }

  private val binding: Binding by viewBinding(Binding::bind)
  private val args: PromotionItemsFragmentArgs by navArgs()
  private val adapter by unsafeLazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_promotion_items,
    screen = AnalyticsScreen.COUPON_INFO,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    scope.unloadBindingModuleOnClose()
  }

  override fun initViews() {
    super.initViews()
    initRecyclerView()
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

  private fun initRecyclerView() {
    val decoration = ItemDecoration(
      horizontalOffset = resources.getDimensionPixelOffset(R.dimen.spacing_4),
      horizontalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_2),
      verticalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_2),
      topSpacing = resources.getDimensionPixelSize(R.dimen.spacing_16),
      bottomSpacing = resources.getDimensionPixelSize(R.dimen.spacing_10),
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
      this.layoutManager = layoutManager
      adapter = this@PromotionItemsFragment.adapter
      addItemDecoration(decoration)
      addItemDecoration(HomeOffsetDecoration(requireContext()))
    }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    HomeAdapterDelegates.emptyStateDelegate(),
    HomeAdapterDelegates.sectionHeaderDelegate(),
    CommonAdapterDelegates.itemDelegate(),
    PromotionItemsAdapterDelegates.ruleDelegate(),
  )

  private fun renderData(items: List<RecyclerViewItem>) {
    adapter.items = items
  }

  private fun handleEffect(effect: PromotionItemsEffect) {
    @Exhaustive
    when (effect) {
      PromotionItemsEffect.ShowLoadingError -> renderState(isError = true)
    }
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
      recyclerView.setVisible(!isLoading && !isError)
    }
  }
}
