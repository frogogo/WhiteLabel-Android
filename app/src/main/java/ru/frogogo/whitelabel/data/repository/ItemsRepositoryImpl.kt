package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.api.item.Item
import ru.frogogo.whitelabel.data.model.ui.item.ItemInfoUiModel
import ru.frogogo.whitelabel.data.model.ui.item.ItemUiModel
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.apiCall
import ru.frogogo.whitelabel.util.network.mapToResult

class ItemsRepositoryImpl(
  dispatchersProvider: DispatchersProvider,
  private val api: FrogogoApi,
) : Repository(dispatchersProvider),
  ItemsRepository {

  override suspend fun getItems(): Result<List<ItemUiModel>, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getItems() }.mapToResult { items ->
      items.map(Item::toDomain)
    }
  }

  override suspend fun getItem(id: Int): Result<ItemInfoUiModel, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getItem(id) }.mapToResult { item ->
      item.toDomain()
    }
  }
}
