package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.mapper.toDomain
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.network.FrogogoApi
import ru.frogogo.whitelabel.util.dispatcher.DispatchersProvider
import ru.frogogo.whitelabel.util.network.NetworkError
import ru.frogogo.whitelabel.util.network.apiCall
import ru.frogogo.whitelabel.util.network.mapToResult

class HomeRepositoryImpl(
  dispatcher: DispatchersProvider,
  private val api: FrogogoApi,
) : Repository(dispatcher), HomeRepository {

  override suspend fun getHome(): Result<HomeState, NetworkError<ErrorResponse>> = withIOContext {
    apiCall { api.getHome() }.mapToResult { home ->
      home.toDomain()
    }
  }
}
