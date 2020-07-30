package ru.poprobuy.poprobuy.usecase.receipt

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import ru.poprobuy.poprobuy.DataFixtures
import ru.poprobuy.poprobuy.data.repository.ReceiptsRepository
import ru.poprobuy.poprobuy.failureNetworkCall
import ru.poprobuy.poprobuy.successNetworkCall
import ru.poprobuy.poprobuy.usecase.UseCaseResult

@ExperimentalCoroutinesApi
class GetReceiptsUseCaseTest {

  private lateinit var useCase: GetReceiptsUseCase
  private val receiptsRepository: ReceiptsRepository = mockk(relaxed = true)

  @Before
  fun startUp() {
    clearAllMocks()
    useCase = GetReceiptsUseCase(receiptsRepository)
  }

  @Test
  fun `use case returns receipts`() = runBlockingTest {
    val receipts = listOf(DataFixtures.receipt)
    coEvery { receiptsRepository.getReceipts() } returns successNetworkCall(receipts)

    val result = useCase()

    result.shouldBeInstanceOf(UseCaseResult.Success::class)
    (result as UseCaseResult.Success).data shouldBeEqualTo receipts
  }

  @Test
  fun `use case returns failure in network error`() = runBlockingTest {
    coEvery { receiptsRepository.getReceipts() } returns failureNetworkCall()

    val result = useCase()

    result.shouldBeInstanceOf(UseCaseResult.Failure::class)
  }

}
