package ru.poprobuy.poprobuy.data.repository

import ru.poprobuy.poprobuy.data.mapper.toDomain
import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.data.network.PoprobuyApi
import ru.poprobuy.poprobuy.util.Result
import ru.poprobuy.poprobuy.util.network.NetworkError
import ru.poprobuy.poprobuy.util.network.apiCall
import ru.poprobuy.poprobuy.util.network.mapToResult

// TODO: 26.11.2020 Tests
class HomeRepository(
  private val api: PoprobuyApi,
) {

  suspend fun getHome(): Result<HomeState, NetworkError<ErrorResponse>> =
    apiCall { api.getHome() }.mapToResult { home ->
      home.toDomain()
    }

}
