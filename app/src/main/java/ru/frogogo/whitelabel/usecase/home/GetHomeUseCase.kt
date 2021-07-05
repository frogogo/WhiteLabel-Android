package ru.frogogo.whitelabel.usecase.home

import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.core.recreateWithSuccess
import ru.frogogo.whitelabel.core.successOrNull
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.repository.HomeRepository
import ru.frogogo.whitelabel.usecase.GetItemsUseCase
import ru.frogogo.whitelabel.util.network.NetworkError

class GetHomeUseCase(
  private val homeRepository: HomeRepository,
  private val getItemsUseCase: GetItemsUseCase,
) {

  suspend operator fun invoke(): Result<HomeState, NetworkError<ErrorResponse>> {
    val homeResponse = homeRepository.getHome()
    val homeState = homeResponse.successOrNull()

    return if (homeState is HomeState.Empty) {
      homeState.items.addAll(getItemsUseCase.invoke().successOrNull().orEmpty())

      homeResponse.recreateWithSuccess(homeState)
    } else {
      homeResponse
    }
  }
}
