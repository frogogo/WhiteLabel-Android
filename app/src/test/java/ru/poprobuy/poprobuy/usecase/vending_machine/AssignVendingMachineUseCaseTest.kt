package ru.poprobuy.poprobuy.usecase.vending_machine

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.machine.VendingMachineUiModel
import ru.poprobuy.poprobuy.data.repository.VendingMachineRepository
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.test.test422Error
import ru.poprobuy.test.testError

@ExperimentalCoroutinesApi
internal class AssignVendingMachineUseCaseTest {

  private lateinit var useCase: AssignVendingMachineUseCase

  private val vendingMachineRepository: VendingMachineRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = AssignVendingMachineUseCase(
      vendingMachineRepository = vendingMachineRepository
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should return success`() = runBlockingTest {
    val receiptId = 21
    val machineId = "3278"
    val response = Result.Success<VendingMachineUiModel, NetworkError<ErrorResponse>>(
      DataFixtures.getVendingMachineUiModel()
    )
    coEvery { vendingMachineRepository.assignMachine(any(), any()) } returns response

    val result = useCase(machineId = machineId, receiptId = receiptId)

    result shouldBeEqualTo AssignVendingMachineUseCaseResult.Success(response.value)
    coVerifySequence {
      vendingMachineRepository.assignMachine(machineId = machineId, receiptId = receiptId)
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return machine not found`() = runBlockingTest {
    val receiptId = 21
    val machineId = "3278"
    coEvery { vendingMachineRepository.assignMachine(any(), any()) }
      .returns(Result.Failure(NetworkError.testError(404)))

    val result = useCase(machineId = machineId, receiptId = receiptId)

    result shouldBeEqualTo AssignVendingMachineUseCaseResult.MachineNotFound
    coVerifySequence {
      vendingMachineRepository.assignMachine(machineId = machineId, receiptId = receiptId)
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return validation failure`() = runBlockingTest {
    val receiptId = 21
    val machineId = "3278"
    val response = NetworkError.test422Error("error")
    coEvery { vendingMachineRepository.assignMachine(any(), any()) } returns Result.Failure(response)

    val result = useCase(machineId = machineId, receiptId = receiptId)

    result shouldBeEqualTo AssignVendingMachineUseCaseResult.ValidationFailure("error")
    coVerifySequence {
      vendingMachineRepository.assignMachine(machineId = machineId, receiptId = receiptId)
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return failure`() = runBlockingTest {
    val receiptId = 21
    val machineId = "3278"
    coEvery { vendingMachineRepository.assignMachine(any(), any()) } returns Result.Failure(NetworkError.IOError())

    val result = useCase(machineId = machineId, receiptId = receiptId)

    result shouldBeEqualTo AssignVendingMachineUseCaseResult.Failure
    coVerifySequence {
      vendingMachineRepository.assignMachine(machineId = machineId, receiptId = receiptId)
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(vendingMachineRepository)
  }
}
