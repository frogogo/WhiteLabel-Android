package ru.poprobuy.poprobuy.ui.profile

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.data.model.ui.profile.ProfileUiModel
import ru.poprobuy.poprobuy.databinding.FragmentProfileBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener
import ru.poprobuy.poprobuy.extension.setVisible

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {

  override val viewModel: ProfileViewModel by viewModel()

  private val binding: FragmentProfileBinding by viewBinding()

  override fun initViews() {
    binding.buttonClose.setOnSafeClickListener(viewModel::navigateBack)
    binding.layoutContent.apply {
      layoutInvite.buttonShare.setOnSafeClickListener { /* TODO: 04.07.2020 Invitation */ }
      layoutMenu.apply {
        buttonReceipts.setOnSafeClickListener(viewModel::navigateToReceipts)
        buttonGoods.setOnSafeClickListener(viewModel::navigateToGoods)
      }
    }
  }

  override fun initObservers() = viewModel.run {
    profileLive.observe(this@ProfileFragment::renderProfile)
  }

  private fun renderProfile(profile: ProfileUiModel) {
    binding.layoutContent.apply {
      // TransitionManager.beginDelayedTransition(root)

      // Set data
      textViewUserName.text = profile.name
      textViewPhoneNumber.text = profile.phoneNumber
      textViewEmail.text = profile.email
      textViewVersion.text = profile.appVersion

      // Show content
      root.setVisible(true)
    }
  }

}
