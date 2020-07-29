package ru.poprobuy.poprobuy.ui.profile

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.BuildConfig
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.databinding.FragmentProfileBinding
import ru.poprobuy.poprobuy.di.observe
import ru.poprobuy.poprobuy.extension.formatWithMask
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.util.Constants

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {

  override val viewModel: ProfileViewModel by viewModel()

  private val binding: FragmentProfileBinding by viewBinding()

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
    viewModel.run {
      observe(profileLive, this@ProfileFragment::renderProfile)
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
