package ru.poprobuy.poprobuy.ui.profile

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.BuildConfig
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.databinding.FragmentProfileBinding
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
    viewModel.stateLiveData.observe(this::renderState)
  }

  private fun renderState(state: ProfileViewModel.ViewState) {
    state.profile?.let(this::renderProfile)

    binding.apply {
      progressBar.setVisible(state.isLoading)
      viewErrorState.setVisible(state.isError)
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
