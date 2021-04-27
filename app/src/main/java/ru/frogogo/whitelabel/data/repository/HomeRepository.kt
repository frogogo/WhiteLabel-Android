package ru.frogogo.whitelabel.data.repository

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.util.network.NetworkError

interface HomeRepository {

  suspend fun getHome(): Result<HomeState, NetworkError<ErrorResponse>>
}
