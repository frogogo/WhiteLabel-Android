package ru.frogogo.whitelabel.ui.onboarding

import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.recycler.BaseDelegationAdapter
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentOnboardingBinding
import ru.frogogo.whitelabel.extension.*
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.util.unsafeLazy

class OnboardingFragment : BaseFragment<OnboardingViewModel>() {

  override val viewModel: OnboardingViewModel by viewModel()

  private val binding: FragmentOnboardingBinding by viewBinding()
  private val adapter: BaseDelegationAdapter by unsafeLazy { createAdapter() }

  private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      updateButtons()
      onBackPressCallback.isEnabled = position != 0
    }
  }

  /**
   * Navigates back in the ViewPager
   */
  private val onBackPressCallback = object : OnBackPressedCallback(false) {
    override fun handleOnBackPressed() {
      binding.viewPager.currentItem--
    }
  }

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_onboarding,
    screen = AnalyticsScreen.ONBOARDING
  )

  override fun initViews() {
    binding.run {
      // Init view pager
      viewPager.apply {
        adapter = this@OnboardingFragment.adapter
        offscreenPageLimit = 3
        registerOnPageChangeCallback(pageChangeCallback)
        setPageTransformer(OnboardingTransformation())
      }

      // Init circle indicator
      circleIndicator.setViewPager(viewPager)
      adapter.registerAdapterDataObserver(circleIndicator.adapterDataObserver)

      // Init buttons
      buttonNext.setOnClickListener { viewPager.goNext() }
      buttonFinish.setOnSafeClickListener(viewModel::completeOnboarding)
    }

    // Handle view pager back navigation
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressCallback)
  }

  override fun initObservers() {
    observe(viewModel.pagesLive) { data ->
      adapter.items = data
      updateButtons()
    }
  }

  /**
   * Toggles buttons depending on the current page
   *
   * buttonNext should be active on all pages except the last one
   * buttonFinish should be active only on the last page
   */
  private fun updateButtons() {
    binding.apply {
      val hasNext = viewPager.hasNext()
      buttonNext.setVisible(hasNext, useInvisible = true)
      buttonFinish.setVisible(!hasNext, useInvisible = true)
    }
  }

  private fun createAdapter() = BaseDelegationAdapter(
    OnboardingAdapterDelegates.pageDelegate()
  )
}
