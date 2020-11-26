package ru.poprobuy.poprobuy.ui.machine_select

import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.poprobuy.poprobuy.R
import ru.poprobuy.poprobuy.arch.ui.BaseFragment
import ru.poprobuy.poprobuy.databinding.FragmentMachineSelectBinding
import ru.poprobuy.poprobuy.extension.*
import ru.poprobuy.poprobuy.extension.binding.initMachineNumberType
import ru.poprobuy.poprobuy.util.ParallelAutoTransition
import ru.poprobuy.poprobuy.util.analytics.AnalyticsScreen
import ru.poprobuy.poprobuy.view.dialog.ErrorDialogFragment
import ru.poprobuy.poprobuy.view.dialog.ErrorDialogFragment.Companion.showIn

class MachineSelectFragment : BaseFragment<MachineSelectViewModel>(
  layoutId = R.layout.fragment_machine_select,
  screen = AnalyticsScreen.MACHINE_SELECT,
  windowAnimations = true
) {

  override val viewModel: MachineSelectViewModel by viewModel { parametersOf(args.receiptId) }

  private val binding: FragmentMachineSelectBinding by viewBinding()
  private val args: MachineSelectFragmentArgs by navArgs()

  override fun initViews() {
    binding.apply {
      textInputLayout.apply {
        initMachineNumberType()
        setEditGoAction { selectMachine() }
        post { showKeyboard() }
      }
      buttonBack.setOnSafeClickListener {
        requireActivity().hideKeyboard()
        viewModel.navigateBack()
      }
      buttonContinue.setOnSafeClickListener { selectMachine() }
    }
  }

  override fun initObservers() {
    viewModel.apply {
      observe(isLoadingLive, ::renderLoading)
      observe(commandLiveEvent, ::handleCommand)
    }
  }

  private fun renderLoading(isLoading: Boolean) {
    binding.textInputLayout.setLoading(isLoading)
  }

  private fun handleCommand(command: MachineSelectCommand) {
    TransitionManager.beginDelayedTransition(binding.layoutContent, ParallelAutoTransition().apply {
      excludeChildren(binding.textInputLayout, true)
    })
    @Exhaustive
    when (command) {
      is MachineSelectCommand.ShowDialogError -> {
        ErrorDialogFragment.newInstance(command.error)
          .showIn(childFragmentManager)
      }
      is MachineSelectCommand.ShowFieldError -> {
        binding.apply {
          textViewError.setNullableTextRes(command.errorResId)
          textInputLayout.setError(command.errorResId != null)
        }
      }
    }
  }

  private fun selectMachine() {
    viewModel.selectMachine(binding.textInputLayout.text)
  }

}
