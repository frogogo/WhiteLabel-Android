package ru.frogogo.whitelabel.usecase.item

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.data.repository.ItemsRepository
import ru.frogogo.whitelabel.util.network.NetworkError

class GetItemsUseCase(
  private val itemsRepository: ItemsRepository,
) {

  suspend operator fun invoke(): Result<List<ItemUiModel>, NetworkError<ErrorResponse>> =
    itemsRepository.getItems()
}
