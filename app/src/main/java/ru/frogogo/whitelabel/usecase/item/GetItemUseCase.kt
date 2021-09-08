package ru.frogogo.whitelabel.usecase.item

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.item.ItemInfoUiModel
import ru.frogogo.whitelabel.data.repository.ItemsRepository
import ru.frogogo.whitelabel.util.network.NetworkError

class GetItemUseCase(
  private val itemsRepository: ItemsRepository,
) {

  suspend operator fun invoke(id: Int): Result<ItemInfoUiModel, NetworkError<ErrorResponse>> =
    itemsRepository.getItem(id)
}
