package ru.poprobuy.poprobuy.ui.auth.policy

import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.arch.ui.viewBinding
import ru.poprobuy.poprobuy.databinding.FragmentAuthPolicyBinding
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener

class AuthPolicyFragment : BaseFragment<AuthPolicyViewModel>(R.layout.fragment_auth_policy) {

  override val viewModel: AuthPolicyViewModel by viewModel()

  private val binding: FragmentAuthPolicyBinding by viewBinding()

  override fun initViews() {
    binding.buttonContinue.setOnSafeClickListener { viewModel.navigateNext() }
  }

}
