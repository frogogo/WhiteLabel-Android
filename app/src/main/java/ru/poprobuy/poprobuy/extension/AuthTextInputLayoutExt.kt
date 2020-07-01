package ru.poprobuy.poprobuy.extension

import android.text.InputType
import android.text.method.DigitsKeyListener
import com.redmadrobot.inputmask.MaskedTextChangedListener
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.util.Constants
import ru.poprobuy.poprobuy.view.AuthTextInputLayout

private const val DIGITS_PHONE_NUMBER = "1234567890+-() "
private const val DIGITS_CONFIRMATION_CODE = "1234567890"

fun AuthTextInputLayout.initPhoneType(listener: MaskedTextChangedListener.ValueListener) {
  // binding.apply {
  binding.editText.inputType = InputType.TYPE_CLASS_NUMBER
  textKeyListener = DigitsKeyListener.getInstance(DIGITS_PHONE_NUMBER)

  // Set masked listener
  val textChangedListener = MaskedTextChangedListener.installOn(
    editText = binding.editText,
    primaryFormat = Constants.PHONE_MASK,
    valueListener = listener
  )

  // Set placeholder
  placeholder = textChangedListener.placeholder()
  // Set prefix
  // Use space at the end to add some margin between prefix and main text
  prefix = "${Constants.PHONE_PREFIX} "
}

fun AuthTextInputLayout.initCodeConfirmationType() {
  binding.apply {
    editText.apply {
      setMaxLength(Constants.CONFIRMATION_CODE_LENGTH)
      inputType = InputType.TYPE_CLASS_NUMBER
      textKeyListener = DigitsKeyListener.getInstance(DIGITS_CONFIRMATION_CODE)
      setTextAppearance(R.style.AuthCodeConfirmation_EditText)
    }
    textViewPlaceholder.setTextAppearance(R.style.AuthCodeConfirmation)
    placeholder = "・・・・"
  }
}

fun AuthTextInputLayout.initUserNameType() {
  binding.editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
}

fun AuthTextInputLayout.initEmailType() {
  binding.editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
}
