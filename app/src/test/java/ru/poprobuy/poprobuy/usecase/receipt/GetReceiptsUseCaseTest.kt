package ru.poprobuy.poprobuy.usecase.receipt

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
import ru.poprobuy.test.DataFixtures
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.receipt.ReceiptUiModel
import ru.poprobuy.poprobuy.data.repository.ReceiptsRepository
import ru.poprobuy.poprobuy.util.network.NetworkError

@ExperimentalCoroutinesApi
class GetReceiptsUseCaseTest {

  private lateinit var useCase: GetReceiptsUseCase
  private val receiptsRepository: ReceiptsRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = GetReceiptsUseCase(
      receiptsRepository = receiptsRepository
    )
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Test
  fun `useCase should return result`() = runBlockingTest {
    val receipts = Result.Success<List<ReceiptUiModel>, NetworkError<ErrorResponse>>(
      listOf(DataFixtures.getReceiptUIModel())
    )
    coEvery { receiptsRepository.getReceipts() } returns receipts

    val result = useCase()

    result shouldBeEqualTo receipts
    coVerifySequence {
      receiptsRepository.getReceipts()
    }
  }
}
