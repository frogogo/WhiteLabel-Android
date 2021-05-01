package ru.frogogo.whitelabel.usecase.receipt

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.frogogo.test.DataFixtures
import ru.frogogo.whitelabel.data.repository.ReceiptsRepository
import ru.frogogo.test.test422Error
import ru.frogogo.test.testError
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.NetworkError

@ExperimentalCoroutinesApi
internal class CreateReceiptUseCaseTest {

  private lateinit var useCase: CreateReceiptUseCase

  private val receiptsRepository: ReceiptsRepository = mockk(relaxed = true)

  @BeforeEach
  fun startUp() {
    useCase = CreateReceiptUseCase(
      receiptsRepository = receiptsRepository
    )
  }

  @Test
  fun `useCase should return success`() = runBlockingTest {
    coEvery { receiptsRepository.activateQrString(any()) } returns Result.Success(Unit)

    val result = useCase(DataFixtures.QR_RECEIPT)

    result shouldBeEqualTo CreateReceiptResult.Success
    coVerifySequence {
      receiptsRepository.activateQrString(DataFixtures.QR_RECEIPT)
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return validation error`() = runBlockingTest {
    coEvery { receiptsRepository.activateQrString(any()) }
      .returns(Result.Failure(NetworkError.test422Error("test")))

    val result = useCase(DataFixtures.QR_RECEIPT)

    result shouldBeEqualTo CreateReceiptResult.ValidationError("test")
    coVerifySequence {
      receiptsRepository.activateQrString(DataFixtures.QR_RECEIPT)
    }
    confirmVerified()
  }

  @Test
  fun `useCase should return failure`() = runBlockingTest {
    coEvery { receiptsRepository.activateQrString(any()) } returns Result.Failure(NetworkError.testError())

    val result = useCase(DataFixtures.QR_RECEIPT)

    result shouldBeEqualTo CreateReceiptResult.Error
    coVerifySequence {
      receiptsRepository.activateQrString(DataFixtures.QR_RECEIPT)
    }
    confirmVerified()
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  private fun confirmVerified() {
    confirmVerified(receiptsRepository)
  }
}
