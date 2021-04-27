package ru.frogogo.whitelabel.ui.machine_select

import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import app.cash.exhaustive.Exhaustive
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.frogogo.whitelabel.R
import ru.frogogo.whitelabel.core.ui.BaseFragment
import ru.frogogo.whitelabel.databinding.FragmentMachineSelectBinding
import ru.frogogo.whitelabel.extension.binding.initMachineNumberType
import ru.frogogo.whitelabel.extension.hideKeyboard
import ru.frogogo.whitelabel.extension.observe
import ru.frogogo.whitelabel.extension.setNullableTextRes
import ru.frogogo.whitelabel.extension.setOnSafeClickListener
import ru.frogogo.whitelabel.util.ParallelAutoTransition
import ru.frogogo.whitelabel.util.analytics.AnalyticsScreen
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment
import ru.frogogo.whitelabel.view.dialog.ErrorDialogFragment.Companion.showIn

class MachineSelectFragment : BaseFragment<MachineSelectViewModel>() {

  override val viewModel: MachineSelectViewModel by viewModel { parametersOf(args.receiptId) }

  private val binding: FragmentMachineSelectBinding by viewBinding()
  private val args: MachineSelectFragmentArgs by navArgs()

  override fun provideConfiguration(): Configuration = Configuration(
    layoutId = R.layout.fragment_machine_select,
    screen = AnalyticsScreen.MACHINE_SELECT,
    windowAnimations = true
  )

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
