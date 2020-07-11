package ru.poprobuy.poprobuy.ui.home

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.BaseDelegationAdapter
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentHomeBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home), SwipeRefreshLayout.OnRefreshListener {

  override val viewModel: HomeViewModel by viewModel()

  private val binding: FragmentHomeBinding by viewBinding()
  private val adapter by lazy { createAdapter() }

  override fun initViews() {
    binding.apply {
      buttonProfile.setOnSafeClickListener { viewModel.navigateToProfile() }
      recyclerView.adapter = this@HomeFragment.adapter
      swipeRefreshLayout.setOnRefreshListener(this@HomeFragment)
    }
  }

  override fun initObservers() {
    viewModel.run {
      dataLive.observe { data ->
        adapter.items = data
        binding.progressBar.setVisible(false)
        binding.swipeRefreshLayout.isRefreshing = false
      }
    }
  }

  override fun onRefresh() {
    viewModel.refreshData()
  }

  private fun createAdapter(): BaseDelegationAdapter = BaseDelegationAdapter(
    HomeAdapterDelegates.emptyStateDelegate(viewModel::navigateToReceiptScan),
    HomeAdapterDelegates.acceptedStateDelegate(
      scanMachineCallback = viewModel::navigateToMachineScan,
      enterMachineAction = viewModel::navigateToMachineEnter,
      scanReceiptAction = viewModel::navigateToReceiptScan
    )
  )

}
