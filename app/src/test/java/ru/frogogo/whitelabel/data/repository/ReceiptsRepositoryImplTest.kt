package ru.frogogo.whitelabel.data.repository

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import ru.frogogo.test.DataFixtures
import ru.frogogo.test.base.RepositoryTest
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.receipt.ReceiptCreationRequest
import ru.frogogo.whitelabel.data.network.FrogogoApi

@ExperimentalCoroutinesApi
internal class ReceiptsRepositoryImplTest : RepositoryTest() {

  private lateinit var repository: ReceiptsRepository

  private val api: FrogogoApi = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    repository = ReceiptsRepositoryImpl(
      dispatcher = coroutineTestExtension.testDispatcherProvider,
      api = api
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun getReceipts() = runBlockingTest {
    val receipts = List(10) { DataFixtures.getReceipt(it) }
    coEvery { api.getReceipts() } returns Response.success(receipts)

    val result = repository.getReceipts()

    result shouldBeEqualTo Result.Success(receipts.map { it.toDomain() })
    coVerifySequence {
      api.getReceipts()
    }
    confirmVerified()
  }

  @Test
  fun activateQrString() = runBlockingTest {
    coEvery { api.createReceipt(any()) } returns Response.success(Unit)

    val result = repository.activateQrString(DataFixtures.QR_RECEIPT)

    result shouldBeEqualTo Result.Success(Unit)
    coVerifySequence {
      api.createReceipt(ReceiptCreationRequest(DataFixtures.QR_RECEIPT))
    }
    confirmVerified()
  }

  private fun confirmVerified() {
    confirmVerified(api)
  }
}
