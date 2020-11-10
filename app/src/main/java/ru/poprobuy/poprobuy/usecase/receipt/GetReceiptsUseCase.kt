package ru.poprobuy.poprobuy.usecase.receipt

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.model.api.receipt.Receipt
import ru.poprobuy.poprobuy.data.repository.ReceiptsRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkResource

class GetReceiptsUseCase(
  private val receiptsRepository: ReceiptsRepository,
) {

  suspend operator fun invoke(): UseCaseResult<List<Receipt>, Any> =
    when (val result = receiptsRepository.getReceipts()) {
      is NetworkResource.Success -> {
        i { "Receipts successfully fetched" }
        UseCaseResult.Success(result.data)
      }
      is NetworkResource.Error -> {
        e { "Receipts fetching failed" }
        UseCaseResult.Failure(Unit)
      }
    }
}
