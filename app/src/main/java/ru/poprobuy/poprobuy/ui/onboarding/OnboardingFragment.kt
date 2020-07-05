package ru.poprobuy.poprobuy.ui.onboarding

import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.recycler.BaseDelegationAdapter
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentOnboardingBinding
import ru.poprobuy.poprobuy.extension.goNext
import ru.poprobuy.poprobuy.extension.hasNext
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

class OnboardingFragment : BaseFragment<OnboardingViewModel>(R.layout.fragment_onboarding) {

  override val viewModel: OnboardingViewModel by viewModel()

  private val binding: FragmentOnboardingBinding by viewBinding()
  private val adapter: BaseDelegationAdapter by lazy { createAdapter() }

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
      buttonNext.setOnClickListener { if (viewPager.hasNext()) viewPager.goNext() }
      buttonFinish.setOnSafeClickListener { viewModel.completeOnboarding() }
    }

    // Handle view pager back navigation
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressCallback)
  }

  override fun initObservers() = viewModel.run {
    pagesLive.observe {
      adapter.items = it
      updateButtons()
    }
  }

  /**
   * Toggles buttons depending on the current page
   *
   * buttonNext should be active on all pages except the last one
   * buttonFinish should be active only on the last page
   */
  private fun updateButtons() = binding.run {
    buttonNext.setVisible(viewPager.hasNext(), useInvisible = true)
    buttonFinish.setVisible(!viewPager.hasNext(), useInvisible = true)
  }

  private fun createAdapter() = BaseDelegationAdapter(
    OnboardingAdapterDelegates.pageDelegate()
  )
}
