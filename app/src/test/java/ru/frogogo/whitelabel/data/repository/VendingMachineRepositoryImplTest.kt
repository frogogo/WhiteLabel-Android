package ru.frogogo.whitelabel.data.repository

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.machine.TakeProductRequest
import ru.frogogo.whitelabel.data.model.api.machine.VendingMachineAssignRequest
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.test.DataFixtures
import ru.frogogo.test.base.RepositoryTest

@ExperimentalCoroutinesApi
internal class VendingMachineRepositoryImplTest : RepositoryTest() {

  private lateinit var repository: VendingMachineRepository

  private val api: FrogogoApi = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = VendingMachineRepositoryImpl(
      dispatcher = coroutineTestExtension.testDispatcherProvider,
      api = api
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun assignMachine() = runBlockingTest {
    val machineId = "id"
    val receiptId = 1
    val vendingMachine = DataFixtures.getVendingMachine(1)
    coEvery { api.assignVendingMachine(any(), any()) } returns Response.success(vendingMachine)

    val result = repository.assignMachine(machineId, receiptId)

    result shouldBeEqualTo Result.Success(vendingMachine.toDomain())
    coVerifySequence {
      api.assignVendingMachine(machineId, VendingMachineAssignRequest(receiptId))
    }
  }

  @Test
  fun takeProduct() = runBlockingTest {
    val machineId = 1
    val receiptId = 2
    val productId = 3
    val vendingCellId = 4
    coEvery { api.takeProduct(any(), any()) } returns Response.success(Unit)

    val result = repository.takeProduct(machineId, receiptId, productId, vendingCellId)

    result shouldBeEqualTo Result.Success(Unit)
    coVerifySequence {
      api.takeProduct(
        machineId = machineId,
        body = TakeProductRequest(
          itemId = productId,
          receiptId = receiptId,
          vendingCellId = vendingCellId
        )
      )
    }
  }
}
