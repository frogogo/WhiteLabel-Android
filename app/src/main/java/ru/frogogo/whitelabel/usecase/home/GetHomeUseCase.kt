package ru.frogogo.whitelabel.usecase.home

import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.repository.HomeRepository
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.NetworkError

class GetHomeUseCase(
  private val homeRepository: HomeRepository,
) {

  suspend operator fun invoke(): Result<HomeState, NetworkError<ErrorResponse>> =
    homeRepository.getHome()
}
