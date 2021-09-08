package ru.frogogo.whitelabel.usecase.home

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.core.successOrNull
import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.model.ui.home.HomeState
import ru.frogogo.whitelabel.data.repository.HomeRepository
import ru.frogogo.whitelabel.usecase.item.GetItemsUseCase
import ru.frogogo.whitelabel.util.network.NetworkError

class GetHomeUseCase(
  private val homeRepository: HomeRepository,
  private val getItemsUseCase: GetItemsUseCase,
) {

  suspend operator fun invoke(): Result<HomeState, NetworkError<ErrorResponse>> =
    coroutineScope {
      val homeResponseDeferred = async { homeRepository.getHome() }
      val itemsResponseDeferred = async { getItemsUseCase.invoke() }

      val homeResponse = homeResponseDeferred.await()

      homeResponse.successOrNull()?.apply {
        items.addAll(itemsResponseDeferred.await().successOrNull().orEmpty())
      }

      homeResponse
    }
}
