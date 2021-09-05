package ru.frogogo.whitelabel.ui.auth.email

import android.text.method.LinkMovementMethod
import androidx.navigation.fragment.navArgs
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentAuthEmailBinding
import ru.frogogo.whitelabel.extension.binding.initEmailType
import ru.frogogo.whitelabel.extension.hideKeyboard
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setNullableTextRes
import ru.frogogo.whitelabel.extension.setSafeOnClickListener
import ru.frogogo.whitelabel.util.SpannableUtils
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen

class AuthEmailFragment : BaseFragment<AuthEmailViewModel>() {

  override val viewModel: AuthEmailViewModel by viewModel { parametersOf(args.name) }

  private val binding: FragmentAuthEmailBinding by viewBinding()
  private val args: AuthEmailFragmentArgs by navArgs()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_auth_email,
    screen = AnalyticsScreen.AUTH_EMAIL,
    windowAnimations = true,
  )

  override fun initViews() {
    binding.apply {
      textViewTitle.text = getString(R.string.auth_email_title, args.name)
      textInputLayout.apply {
        initEmailType()
        setEditGoAction { setEmail() }
        showKeyboard()
      }
      buttonContinue.setSafeOnClickListener { setEmail() }
      textViewError.movementMethod = LinkMovementMethod.getInstance()
    }
  }

  override fun initObservers() {
    with(viewModel) {
      observe(isLoadingLive) { isLoading ->
        binding.textInputLayout.setLoading(isLoading)
        binding.buttonContinue.isEnabled = !isLoading
      }
      observe(commandLiveEvent, ::handleCommand)
    }
  }

  override fun hideKeyboard() {
    super.hideKeyboard()
    binding.textInputLayout.hideKeyboard()
  }

  private fun handleCommand(command: AuthEmailCommand) {
    @Exhaustive
    when (command) {
      AuthEmailCommand.SomethingWentWrong -> binding.apply {
        textInputLayout.setError(true)
        textViewError.text = SpannableUtils.createSomethingWentWrongSpan(requireContext()) {
          setEmail()
        }
      }
      is AuthEmailCommand.EmailValidationResult -> binding.apply {
        textViewError.setNullableTextRes(command.errorRes)
        textInputLayout.setError(command.errorRes != null)
      }
    }
  }

  private fun setEmail() {
    viewModel.updateUserData(binding.textInputLayout.text)
  }
}
