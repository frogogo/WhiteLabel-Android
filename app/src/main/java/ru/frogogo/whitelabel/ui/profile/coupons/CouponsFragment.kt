package ru.frogogo.whitelabel.ui.profile.coupons

import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentCouponsBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.ui.common.CommonAdapterDelegates
import ru.frogogo.whitelabel.util.ItemDecoration
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

private typealias Binding = FragmentCouponsBinding

class CouponsFragment : BaseFragment<CouponsViewModel>(),
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: CouponsViewModel by viewModel()

  private val binding: Binding by viewBinding(Binding::bind)
  private val adapter: BaseDelegationAdapter by lazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_coupons,
    screen = AnalyticsScreen.COUPONS,
  )

  override fun initViews() {
    super.initViews()
    initRecyclerView()
    binding.toolbar.apply {
      setBackButtonListener(viewModel::onBackButtonClicked)
      attachToRecyclerView(binding.recyclerView)
    }
  }

  override fun initObservers() {
    super.initObservers()
    viewModel.apply {
      observe(dataLive) { adapter.items = it }
      observe(isLoadingLive) { isLoading ->
        renderState(isLoading = isLoading)
      }
      observe(effectLiveEvent, ::handleEffect)
    }
  }

  private fun initRecyclerView() {
    val decoration = ItemDecoration(
      verticalSpacing = requireContext().resources.getDimensionPixelSize(R.dimen.spacing_3),
      horizontalSpacing = requireContext().resources.getDimensionPixelSize(R.dimen.spacing_4),
      topSpacing = requireContext().resources.getDimensionPixelSize(R.dimen.spacing_4),
      bottomSpacing = requireContext().resources.getDimensionPixelSize(R.dimen.spacing_6)
    )
    binding.recyclerView.apply {
      adapter = this@CouponsFragment.adapter
      addItemDecoration(decoration)
    }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    CommonAdapterDelegates.couponDelegate { viewModel.onCouponClicked(it) },
    CouponsAdapterDelegates.emptyState(),
  )

  private fun handleEffect(effect: CouponsEffect) {
    @Exhaustive
    when (effect) {
      CouponsEffect.ShowLoadingError -> showLoadingError()
    }
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
      recyclerView.setVisible(!isLoading && !isError)
    }
  }

  private fun showLoadingError() {
    renderState(isError = true)
  }
}
