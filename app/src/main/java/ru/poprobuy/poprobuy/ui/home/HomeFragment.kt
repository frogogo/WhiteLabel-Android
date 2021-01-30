package ru.poprobuy.poprobuy.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.core.recycler.BaseDelegationAdapter
import ru.poprobuy.poprobuy.core.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentHomeBinding
import ru.poprobuy.poprobuy.extension.observe
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen
import ru.poprobuy.poprobuy.util.unsafeLazy

class HomeFragment : BaseFragment<HomeViewModel>(
  layoutId = R.layout.fragment_home,
  screen = AnalyticsScreen.HOME
), SwipeRefreshLayout.OnRefreshListener {

  override val viewModel: HomeViewModel by viewModel()

  private val binding: FragmentHomeBinding by viewBinding()
  private val recycledViewPool: RecyclerView.RecycledViewPool by inject()
  private val adapter by unsafeLazy { createAdapter() }

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
      scanMachineCallback = { viewModel.navigateToMachineScan(it) },
      enterMachineAction = { viewModel.navigateToMachineEnter(it) },
      scanReceiptAction = { viewModel.navigateToReceiptScan() }
    )
  )

}
