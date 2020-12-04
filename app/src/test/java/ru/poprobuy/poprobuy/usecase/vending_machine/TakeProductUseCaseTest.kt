package ru.poprobuy.poprobuy.usecase.vending_machine

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.repository.VendingMachineRepository
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

@ExperimentalCoroutinesApi
internal class TakeProductUseCaseTest {

  private lateinit var useCase: TakeProductUseCase

  private val vendingMachineRepository: VendingMachineRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = TakeProductUseCase(
      vendingMachineRepository = vendingMachineRepository
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should return result`() = runBlockingTest {
    val machineId = 1
    val receiptId = 2
    val productId = 3
    val vendingCellId = 4
    val response = Result.Success<Unit, NetworkError<ErrorResponse>>(Unit)
    coEvery { vendingMachineRepository.takeProduct(any(), any(), any(), any()) } returns response

    val result = useCase(machineId, receiptId, productId, vendingCellId)

    result shouldBeEqualTo response
    coVerifySequence {
      vendingMachineRepository.takeProduct(machineId, receiptId, productId, vendingCellId)
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(vendingMachineRepository)
  }

}
