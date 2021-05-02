package ru.frogogo.whitelabel.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentHomeBinding
import ru.frogogo.whitelabel.extension.animateToVisible
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.util.ItemDecoration
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy

class HomeFragment : BaseFragment<HomeViewModel>(),
  SwipeRefreshLayout.OnRefreshListener,
  AndroidScopeComponent {

  override val scope: Scope by fragmentScope()
  override val viewModel: HomeViewModel by viewModel()

  private val binding: FragmentHomeBinding by viewBinding()
  private val recycledViewPool: RecyclerView.RecycledViewPool by inject()
  private val adapter by unsafeLazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_home,
    screen = AnalyticsScreen.HOME
  )

  override fun initViews() {
    initRecyclerView()
    binding.apply {
      buttonProfile.setOnSafeClickListener(viewModel::onProfileClicked)
      swipeRefreshLayout.setOnRefreshListener(this@HomeFragment)
      viewErrorState.setOnRefreshClickListener(viewModel::refreshData)
      buttonScan.setOnSafeClickListener(viewModel::onScanClicked)
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(viewModel.dataLive) { data ->
        binding.swipeRefreshLayout.isRefreshing = false
        adapter.items = data
      }
      observe(isLoadingLive) { isLoading ->
        renderState(isLoading = isLoading)
      }
      observe(effectLiveEvent, ::handleEffect)
      observe(scanButtonStateLive, ::handleScanButtonState)
    }
  }

  override fun onRefresh() {
    viewModel.refreshData()
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
      recyclerView.setVisible(!isLoading && !isError)
    }
  }

  private fun initRecyclerView() {
    val decoration = ItemDecoration(
      verticalSpacing = 0,
      horizontalSpacing = resources.getDimensionPixelSize(R.dimen.spacing_4),
      topSpacing = resources.getDimensionPixelSize(R.dimen.spacing_6),
      bottomSpacing = resources.getDimensionPixelSize(R.dimen.spacing_24)
    )

    binding.recyclerView.apply {
      setRecycledViewPool(this@HomeFragment.recycledViewPool)
      adapter = this@HomeFragment.adapter
      addItemDecoration(decoration)
    }
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    //  HomeAdapterDelegates.emptyStateDelegate { viewModel.onScanClicked() },
    //  HomeAdapterDelegates.approvedStateDelegate(
    //    scanMachineCallback = { /* TODO */ },
    //    enterMachineAction = { /* TODO */ },
    //    scanReceiptAction = { viewModel.onScanClicked() }
    //  )
    HomeAdapterDelegates.couponProgressDelegate(),
  )

  private fun handleEffect(effect: HomeEffect) {
    @Exhaustive
    when (effect) {
      HomeEffect.ShowLoadingError -> {
        binding.swipeRefreshLayout.isRefreshing = false
        renderState(isError = true)
      }
    }
  }

  private fun handleScanButtonState(state: HomeScanButtonState) {
    binding.buttonScan.apply {
      animateToVisible()
      isEnabled = state == HomeScanButtonState.SHOWN_ENABLED
    }
  }
}
