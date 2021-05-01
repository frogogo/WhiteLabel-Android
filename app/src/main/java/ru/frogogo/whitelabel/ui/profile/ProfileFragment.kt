package ru.frogogo.whitelabel.ui.profile

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.frogogo.whitelabel.BuildConfig
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.data.model.ui.profile.ProfileUiModel
import ru.frogogo.whitelabel.databinding.FragmentProfileBinding
import ru.frogogo.whitelabel.extension.formatWithMask
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.util.Constants
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class ProfileFragment : BaseFragment<ProfileViewModel>() {

  override val viewModel: ProfileViewModel by viewModel()

  private val binding: FragmentProfileBinding by viewBinding()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_profile,
    screen = AnalyticsScreen.PROFILE
  )

  override fun initViews() {
    binding.apply {
      buttonClose.setOnSafeClickListener(viewModel::navigateBack)
      viewErrorState.setOnRefreshClickListener(viewModel::loadProfile)
      layoutContent.apply {
        layoutInvite.buttonShare.setOnSafeClickListener { /* TODO: 04.07.2020 Invitation */ }
        layoutMenu.apply {
          buttonReceipts.setOnSafeClickListener(viewModel::navigateToReceipts)
          buttonGoods.setOnSafeClickListener(viewModel::navigateToGoods)
        }
      }
      initLogoutButton()
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(profileLive, ::renderProfile)
      observe(isLoadingLive) { renderState(isLoading = it) }
      observe(errorOccurredLiveEvent) { renderState(isError = true) }
    }
  }

  private fun renderProfile(profile: ProfileUiModel) {
    binding.layoutContent.apply {
      // Set data
      textViewUserName.text = profile.name
      textViewPhoneNumber.text = profile.phoneNumber.formatWithMask(Constants.PHONE_MASK_FULL)
      textViewEmail.text = profile.email
      textViewVersion.text = profile.appVersion

      // Show content
      root.setVisible(true)
    }
  }

  private fun renderState(isLoading: Boolean = false, isError: Boolean = false) {
    binding.apply {
      progressBar.setVisible(isLoading && !isError)
      viewErrorState.setVisible(isError && !isLoading)
    }
  }

  /**
   * DEBUG STUFF
   */
  @Suppress("ConstantConditionIf")
  private fun initLogoutButton() {
    if (!BuildConfig.DEBUG_STUFF) return
    binding.buttonLogout.apply {
      setVisible(true)
      setOnSafeClickListener(viewModel::logout)
    }
  }
}
