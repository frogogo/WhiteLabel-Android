package ru.frogogo.whitelabel.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentHomeBinding
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy

class HomeFragment : BaseFragment<HomeViewModel>(),
  SwipeRefreshLayout.OnRefreshListener {

  override val viewModel: HomeViewModel by viewModel()

  private val binding: FragmentHomeBinding by viewBinding()
  private val recycledViewPool: RecyclerView.RecycledViewPool by inject()
  private val adapter by unsafeLazy { createAdapter() }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_home,
    screen = AnalyticsScreen.HOME
  )

  override fun initViews() {
    binding.apply {
      buttonProfile.setOnSafeClickListener(viewModel::navigateToProfile)
      recyclerView.setRecycledViewPool(recycledViewPool)
      recyclerView.adapter = this@HomeFragment.adapter
      swipeRefreshLayout.setOnRefreshListener(this@HomeFragment)
      viewErrorState.setOnRefreshClickListener(viewModel::refreshData)
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(viewModel.dataLive) { adapter.items = it }
      observe(isLoadingLive) { isLoading ->
        renderState(isLoading = isLoading)
        binding.swipeRefreshLayout.isRefreshing = false
      }
      observe(errorOccurredLiveEvent) { renderState(isError = true) }
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

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    HomeAdapterDelegates.emptyStateDelegate { viewModel.navigateToReceiptScan() },
    HomeAdapterDelegates.approvedStateDelegate(
      scanMachineCallback = { /* TODO */ },
      enterMachineAction = { /* TODO */ },
      scanReceiptAction = { viewModel.navigateToReceiptScan() }
    )
  )
}
