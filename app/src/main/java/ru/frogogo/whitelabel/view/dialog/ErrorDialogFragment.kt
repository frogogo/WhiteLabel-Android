package ru.frogogo.whitelabel.view.dialog

import android.content.DialogInterface
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseDialogFragment
import ru.frogogo.whitelabel.databinding.DialogErrorBinding
import ru.frogogo.whitelabel.extension.setVisible
import ru.frogogo.whitelabel.util.argument

class ErrorDialogFragment : BaseDialogFragment(R.layout.dialog_error) {

  private val binding: DialogErrorBinding by viewBinding()
  private val callbackViewModel: ErrorDialogFragmentCallbackViewModel by sharedViewModel()
  private val errorText: String? by argument(ARG_ERROR_TEXT)
  private val dialogId: Int by argument(ARG_DIALOG_ID)

  override fun onDismiss(dialog: DialogInterface) {
    callbackViewModel.onDismiss(dialogId)
    super.onDismiss(dialog)
  }

  override fun initViews() {
    binding.apply {
      errorText?.let { text ->
        textViewSubtitle.text = text
      } ?: run {
        textViewSubtitle.setVisible(false)
      }
      buttonContinue.setOnClickListener { dismissAllowingStateLoss() }
    }
  }

  companion object : DialogCompanion<ErrorDialogFragment> {

    private const val TAG = "ErrorDialogFragment"
    private const val ARG_ERROR_TEXT = "arg:error_text"
    private const val ARG_DIALOG_ID = "arg:dialog_id"

    private var lastDialogId = 0

    override fun ErrorDialogFragment.showIn(fragmentManager: FragmentManager) {
      show(fragmentManager, TAG)
    }

    fun newInstance(
      errorText: String?,
      dialogId: Int = lastDialogId++,
    ): ErrorDialogFragment = ErrorDialogFragment().apply {
      arguments = bundleOf(
        ARG_ERROR_TEXT to errorText,
        ARG_DIALOG_ID to dialogId
      )
    }

    fun getDialogId(): Int = lastDialogId++
  }
}
