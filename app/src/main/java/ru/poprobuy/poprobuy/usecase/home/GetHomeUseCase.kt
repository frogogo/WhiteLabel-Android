package ru.poprobuy.poprobuy.usecase.home

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.model.ui.home.HomeState
import ru.poprobuy.poprobuy.data.repository.HomeRepository
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

class GetHomeUseCase(
  private val homeRepository: HomeRepository,
) {

  suspend operator fun invoke(): Result<HomeState, NetworkError<ErrorResponse>> =
    homeRepository.getHome()

}
