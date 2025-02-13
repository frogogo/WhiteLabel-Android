package ru.frogogo.whitelabel.extension.binding

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.EditText
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.extension.setMaxLength
import ru.frogogo.whitelabel.util.Constants
import ru.frogogo.whitelabel.view.InputLayout

private const val DIGITS_PHONE_NUMBER = "1234567890+-() "
private const val DIGITS_NUMBERS = "1234567890"

val InputLayout.editText: EditText
  get() = binding.editText

fun InputLayout.initPhoneType() {
  // binding.apply {
  editText.inputType = InputType.TYPE_CLASS_NUMBER
  textKeyListener = DigitsKeyListener.getInstance(DIGITS_PHONE_NUMBER)

  // Set placeholder
  placeholder = context.getString(R.string.auth_phone_placeholder)

  // Set prefix
  prefix = Constants.PHONE_PREFIX
}

fun InputLayout.initCodeConfirmationType() {
  binding.apply {
    editText.apply {
      setMaxLength(Constants.CONFIRMATION_CODE_LENGTH)
      inputType = InputType.TYPE_CLASS_NUMBER
      textKeyListener = DigitsKeyListener.getInstance(DIGITS_NUMBERS)
      setTextAppearance(R.style.AuthCodeConfirmation_EditText)
    }
    textViewPlaceholder.setTextAppearance(R.style.AuthCodeConfirmation)
    placeholder = context.getString(R.string.auth_code_placeholder)
  }
}

fun InputLayout.initUserNameType() {
  editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
}

fun InputLayout.initEmailType() {
  editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
}

fun InputLayout.initMachineNumberType() {
  editText.apply {
    inputType = InputType.TYPE_CLASS_NUMBER
    textKeyListener = DigitsKeyListener.getInstance(DIGITS_NUMBERS)
    setMaxLength(Constants.VENDING_MACHINE_ID_MAX_LENGTH)
  }
}
