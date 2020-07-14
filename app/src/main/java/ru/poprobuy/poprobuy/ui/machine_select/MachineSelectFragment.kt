package ru.poprobuy.poprobuy.ui.machine_select

import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentMachineSelectBinding
import ru.poprobuy.poprobuy.extension.hideKeyboard
import ru.poprobuy.poprobuy.extension.initMachineNumberType
import ru.poprobuy.poprobuy.extension.setOnSafeClickListener

class MachineSelectFragment : BaseFragment<MachineSelectViewModel>(
  layoutId = R.layout.fragment_machine_select,
  windowAnimations = true
) {

  override val viewModel: MachineSelectViewModel by viewModel()

  private val binding: FragmentMachineSelectBinding by viewBinding()

  override fun initViews() {
    binding.apply {
      buttonBack.setOnSafeClickListener {
        requireContext().hideKeyboard()
        viewModel.navigateBack()
      }
      buttonContinue.setOnSafeClickListener { selectMachine() }
      textInputLayout.apply {
        showKeyboard()
        initMachineNumberType()
        setEditGoAction { selectMachine() }
      }
    }
  }

  override fun initObservers() {
    viewModel.apply {
      command.observe { command ->
        when (command) {
          MachineSelectCommand.HideKeyboard -> requireContext().hideKeyboard()
        }
      }
    }
  }

  private fun selectMachine() {
    viewModel.selectMachine(binding.textInputLayout.text)
  }

}
