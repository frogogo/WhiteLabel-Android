package ru.poprobuy.poprobuy.usecase.vending_machine

import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import ru.poprobuy.poprobuy.data.repository.VendingMachineRepository

@ExperimentalCoroutinesApi
internal class AssignVendingMachineUseCaseTest {

  private lateinit var useCase: AssignVendingMachineUseCase

  private val vendingMachineRepository: VendingMachineRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    useCase = AssignVendingMachineUseCase(
      vendingMachineRepository = vendingMachineRepository
    )
  }

  // TODO: 15.11.2020 Fix tests
//  @Test
//  fun `useCase should return result`() = runBlockingTest {
//    val receiptId = 21
//    val machineId = "3278"
//
//    val response = Result.Success<VendingMachineUiModel, NetworkError<ErrorResponse>>(
//      DataFixtures.getVendingMachineUiModel()
//    )
//    coEvery { vendingMachineRepository.assignMachine(any(), any()) } returns response
//
//    val result = useCase(machineId = machineId, receiptId = receiptId)
//
//    result shouldBeEqualTo response
//    coVerifySequence {
//      vendingMachineRepository.assignMachine(machineId = machineId, receiptId = receiptId)
//    }
//  }

}
