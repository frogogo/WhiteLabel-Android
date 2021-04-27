package ru.frogogo.whitelabel.ui.scanner

import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.base.ViewModelTestJUnit5
import ru.frogogo.whitelabel.core.Event
import ru.frogogo.whitelabel.dictionary.ScanMode
import ru.frogogo.whitelabel.usecase.receipt.CreateReceiptUseCase
import ru.frogogo.whitelabel.usecase.vending_machine.AssignVendingMachineUseCase
import ru.frogogo.whitelabel.util.ResourceProvider

@ExperimentalCoroutinesApi
class ScannerViewModelTest : ViewModelTestJUnit5() {

  private val createReceiptUseCase: CreateReceiptUseCase = mockk(relaxed = true)
  private val assignVendingMachineUseCase: AssignVendingMachineUseCase = mockk(relaxed = true)
  private val resourceProvider: ResourceProvider = mockk()

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `view model navigates to machine help`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(
      scanMode = ScanMode.MACHINE,
      receiptId = 1,
      navigation = navigation,
      createReceiptUseCase = createReceiptUseCase,
      assignVendingMachineUseCase = assignVendingMachineUseCase,
      resourceProvider = resourceProvider
    )

    viewModel.navigateToHelp()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToMachineHelp())
  }

  @Test
  fun `view model navigates to receipt help`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(
      scanMode = ScanMode.RECEIPT,
      receiptId = -1,
      navigation = navigation,
      createReceiptUseCase = createReceiptUseCase,
      assignVendingMachineUseCase = assignVendingMachineUseCase,
      resourceProvider = resourceProvider
    )

    viewModel.navigateToHelp()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToReceiptHelp())
  }

  @Test
  fun `view model navigates to manual machine enter`() {
    val navigation = mockk<ScannerNavigation>(relaxed = true)
    val viewModel = ScannerViewModel(
      scanMode = ScanMode.MACHINE,
      receiptId = 1,
      navigation = navigation,
      createReceiptUseCase = createReceiptUseCase,
      assignVendingMachineUseCase = assignVendingMachineUseCase,
      resourceProvider = resourceProvider
    )

    viewModel.navigateToManualMachineEnter()

    viewModel.navigationLiveEvent.value shouldBeEqualTo Event(navigation.navigateToManualMachineEnter(1))
  }
}
