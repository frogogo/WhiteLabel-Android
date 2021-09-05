package ru.frogogo.whitelabel.ui.auth.phone

import android.text.method.LinkMovementMethod
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentAuthPhoneBinding
import ru.frogogo.whitelabel.extension.binding.editText
import ru.frogogo.whitelabel.extension.binding.initPhoneType
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setNullableTextRes
import ru.frogogo.whitelabel.util.Constants
import ru.frogogo.whitelabel.util.ParallelAutoTransition
import ru.frogogo.whitelabel.util.SpannableUtils
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment.Companion.showIn

class AuthPhoneFragment : BaseFragment<AuthPhoneViewModel>(),
  MaskedTextChangedListener.ValueListener {

  override val viewModel: AuthPhoneViewModel by viewModel { parametersOf(args.showLogoutDialog) }

  private val binding: FragmentAuthPhoneBinding by viewBinding()
  private val args: AuthPhoneFragmentArgs by navArgs()
  private var textChangedListener: MaskedTextChangedListener? = null

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_auth_phone,
    screen = AnalyticsScreen.AUTH_PHONE,
    windowAnimations = true,
  )

  override fun initViews() {
    binding.textInputLayout.apply {
      initPhoneType()
      setEditGoAction { viewModel.requestCode(text, true) }
      showKeyboard()
    }
    binding.textViewError.movementMethod = LinkMovementMethod.getInstance()
  }

  override fun initObservers() {
    with(viewModel) {
      observe(commandLiveEvent, ::handleCommand)
      observe(isLoadingLive, binding.textInputLayout::setLoading)
    }
  }

  override fun onStart() {
    super.onStart()
    attachFormatter()
  }

  override fun onStop() {
    super.onStop()
    detachFormatter()
  }

  override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
    viewModel.requestCode(extractedValue)
  }

  private fun handleCommand(command: AuthPhoneCommand) {
    TransitionManager.beginDelayedTransition(
      binding.layoutContent,
      ParallelAutoTransition().apply {
        excludeChildren(binding.textInputLayout, true)
      },
    )
    @Exhaustive
    when (command) {
      AuthPhoneCommand.ClearError -> binding.apply {
        textInputLayout.setError(false)
        textViewError.text = null
      }
      AuthPhoneCommand.SomethingWentWrong -> binding.apply {
        textInputLayout.setError(true)
        textViewError.text = SpannableUtils.createSomethingWentWrongSpan(requireContext()) {
          viewModel.requestCode(textInputLayout.text)
        }
      }
      AuthPhoneCommand.TooManyRequestsError -> binding.apply {
        textInputLayout.setError(true)
        textViewError.setText(R.string.auth_phone_error_too_many_requests)
      }
      AuthPhoneCommand.ShowLogoutDialog -> {
        ErrorDialogFragment.newInstance(getString(R.string.error_logout))
          .showIn(childFragmentManager)
      }
      is AuthPhoneCommand.PhoneValidationResult -> binding.apply {
        textViewError.setNullableTextRes(command.errorRes)
        textInputLayout.setError(command.errorRes != null)
      }
    }
  }

  private fun attachFormatter() {
    textChangedListener = MaskedTextChangedListener.installOn(
      editText = binding.textInputLayout.editText,
      primaryFormat = Constants.PHONE_MASK,
      valueListener = this,
      autocomplete = false, // Prevent handling focus change
    )
  }

  private fun detachFormatter() {
    binding.textInputLayout.editText.removeTextChangedListener(textChangedListener)
  }
}
