package ru.poprobuy.poprobuy.view.dialog

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseDialogFragment
import ru.poprobuy.poprobuy.databinding.DialogErrorBinding
import ru.poprobuy.poprobuy.extension.setVisible
import ru.poprobuy.poprobuy.util.argument

class ErrorDialogFragment : BaseDialogFragment(R.layout.dialog_error) {

  private val binding: DialogErrorBinding by viewBinding()
  private val errorText: String? by argument(ARG_ERROR_TEXT)

  override fun initViews() {
    binding.apply {
      errorText?.let { text ->
        textViewSubtitle.text = text
      } ?: run {
        textViewSubtitle.setVisible(false)
      }
      buttonOk.setOnClickListener { dismissAllowingStateLoss() }
    }
  }

  companion object : DialogCompanion<ErrorDialogFragment> {

    private const val TAG = "ErrorDialogFragment"
    private const val ARG_ERROR_TEXT = "arg:error_text"

    override fun ErrorDialogFragment.showIn(fragmentManager: FragmentManager) {
      show(fragmentManager, TAG)
    }

    fun newInstance(errorText: String?): ErrorDialogFragment = ErrorDialogFragment().apply {
      arguments = bundleOf(
        ARG_ERROR_TEXT to errorText
      )
    }
  }

}
