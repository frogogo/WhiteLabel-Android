package ru.poprobuy.poprobuy.view

import android.content.Context
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.databinding.ViewInputLayoutBinding
import ru.poprobuy.poprobuy.extension.inflateViewBinding
import ru.poprobuy.poprobuy.extension.onImeAction
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.extension.showKeyboard
import ru.poprobuy.poprobuy.extension.withTypedArray

typealias ImeGoAction = () -> Unit

class InputLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

  val binding: ViewInputLayoutBinding = inflateViewBinding()

  /**
   * EditText string value
   */
  var text: String
    get() = binding.editText.text.toString()
    set(value) {
      binding.editText.setText(value)
    }

  /**
   * Placeholder text value
   */
  var placeholder: CharSequence?
    get() = binding.textViewPlaceholder.text
    set(value) {
      binding.textViewPlaceholder.text = value
    }

  /**
   * Prefix text value
   */
  var prefix: CharSequence?
    get() = binding.textViewPrefix.text
    set(value) {
      binding.textViewPrefix.apply {
        text = value
        setVisible(!value.isNullOrEmpty())
      }
    }

  /**
   * EditText's key listener
   */
  var textKeyListener: KeyListener? = binding.editText.keyListener
    set(value) {
      field = value
      binding.editText.keyListener = value
    }

  /** Action to be executed when [EditorInfo.IME_ACTION_GO] is called */
  private var editGoAction: ImeGoAction? = null
  private var isActive: Boolean = true

  init {
    withTypedArray(attrs, R.styleable.InputLayout) {
      if (!isInEditMode) {
        getString(R.styleable.InputLayout_placeholderText)?.let { placeholder = it }
      }
    }

    binding.apply {
      // Activate keyboard when clicking on view
      root.setOnClickListener { if (isActive) showKeyboard() }

      // Handle go action
      editText.onImeAction(EditorInfo.IME_ACTION_GO) { if (isActive) editGoAction?.invoke() }

      // Hide placeholder when edit text is not empty
      editText.doAfterTextChanged { textViewPlaceholder.setVisible(it.isNullOrEmpty()) }
    }
  }

  fun setEditGoAction(action: ImeGoAction) {
    editGoAction = action
  }

  fun setLoading(isLoading: Boolean) {
    binding.apply {
      imageViewError.setVisible(false, useInvisible = true)
      progressBar.setVisible(isLoading, useInvisible = true)
      setActive(!isLoading)
    }
  }

  fun setError(hasError: Boolean) {
    binding.apply {
      imageViewError.setVisible(hasError, useInvisible = true)
      progressBar.setVisible(false, useInvisible = true)
    }
  }

  /**
   * Requests keyboard for EditText
   */
  fun showKeyboard() {
    binding.editText.requestFocus()
    context.showKeyboard(binding.editText)
  }

  /**
   * Toggles EditText mode
   * Makes EditText not editable and focusable if [isActive] == false
   */
  private fun setActive(isActive: Boolean) {
    this.isActive = isActive
    binding.editText.apply {
      isClickable = isActive
      isFocusable = isActive
      isFocusableInTouchMode = isActive
      if (isActive) requestFocus() else clearFocus()
      // keyListener = if (!isActive) null else textKeyListener
    }
  }

}
