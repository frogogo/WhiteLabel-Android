package ru.poprobuy.poprobuy.extension

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.EditText
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.util.Constants
import ru.poprobuy.poprobuy.view.AuthTextInputLayout

private const val DIGITS_PHONE_NUMBER = "1234567890+-() "
private const val DIGITS_NUMBERS = "1234567890"

val AuthTextInputLayout.editText: EditText
  get() = binding.editText

fun AuthTextInputLayout.initPhoneType() {
  // binding.apply {
  editText.inputType = InputType.TYPE_CLASS_NUMBER
  textKeyListener = DigitsKeyListener.getInstance(DIGITS_PHONE_NUMBER)

  // Set placeholder
  placeholder = "(000) 000-00-00"

  // Set prefix
  // Use space at the end to add some margin between prefix and main text
  prefix = "${Constants.PHONE_PREFIX} "
}

fun AuthTextInputLayout.initCodeConfirmationType() {
  binding.apply {
    editText.apply {
      setMaxLength(Constants.CONFIRMATION_CODE_LENGTH)
      inputType = InputType.TYPE_CLASS_NUMBER
      textKeyListener = DigitsKeyListener.getInstance(DIGITS_NUMBERS)
      setTextAppearance(R.style.AuthCodeConfirmation_EditText)
    }
    textViewPlaceholder.setTextAppearance(R.style.AuthCodeConfirmation)
    placeholder = "・・・・"
  }
}

fun AuthTextInputLayout.initUserNameType() {
  editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
}

fun AuthTextInputLayout.initEmailType() {
  editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
}

fun AuthTextInputLayout.initMachineNumberType() {
  editText.apply {
    inputType = InputType.TYPE_CLASS_NUMBER
    textKeyListener = DigitsKeyListener.getInstance(DIGITS_NUMBERS)
  }
}
