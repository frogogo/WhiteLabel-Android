package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.ItemUiModel
import ru.frogogo.whitelabel.util.network.NetworkError

interface ItemsRepository {

  suspend fun getItems(): Result<List<ItemUiModel>, NetworkError<ErrorResponse>>
}
