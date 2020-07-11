package ru.poprobuy.poprobuy.ui.auth.phone

import by.kirich1409.viewbindingdelegate.viewBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentAuthPhoneBinding
import ru.poprobuy.poprobuy.extension.initPhoneType
import ru.poprobuy.poprobuy.extension.setNullableTextRes

class AuthPhoneFragment : BaseFragment<AuthPhoneViewModel>(
  layoutId = R.layout.fragment_auth_phone,
  windowAnimations = true
), MaskedTextChangedListener.ValueListener {

  override val viewModel: AuthPhoneViewModel by viewModel()

  private val binding: FragmentAuthPhoneBinding by viewBinding()

  override fun initViews() {
    // Force keyboard
    binding.textInputLayout.apply {
      showKeyboard()
      setEditGoAction { viewModel.requestCode() }
      initPhoneType(this@AuthPhoneFragment)
    }
  }

  override fun initObservers() = viewModel.run {
    phoneValidationLiveEvent.observe { errorRes ->
      binding.textViewError.setNullableTextRes(errorRes)
      binding.textInputLayout.setError(errorRes != null)
    }
    isLoadingLive.observe { binding.textInputLayout.setLoading(it) }
  }

  override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
    viewModel.setPhoneNumber(extractedValue)
  }

}
