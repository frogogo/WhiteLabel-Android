package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.util.dispatcher.DispatchersProvider
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

class HomeRepository(
  dispatcher: DispatchersProvider,
  private val api: PoprobuyApi,
) : Repository(dispatcher) {

  suspend fun getHome(): Result<HomeState, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getHome() }.mapToResult { home ->
      home.toDomain()
    }
  }

}
