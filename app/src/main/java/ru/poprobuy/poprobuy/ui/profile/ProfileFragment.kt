package ru.poprobuy.poprobuy.ui.profile

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {

  override val viewModel: ProfileViewModel by viewModel()

}
