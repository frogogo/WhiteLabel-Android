package ru.poprobuy.poprobuy.usecase.home

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.model.api.home.HomeResponse
import ru.poprobuy.poprobuy.data.repository.HomeRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkResource

class GetHomeUseCase(
  private val homeRepository: HomeRepository
) {

  suspend operator fun invoke(): UseCaseResult<HomeResponse, Any> = when (val result = homeRepository.getHome()) {
    is NetworkResource.Success -> {
      i { "Home fetched successfully" }
      UseCaseResult.Success(result.data)
    }
    is NetworkResource.Error -> {
      e { "Generic error while fetching home" }
      UseCaseResult.Failure(Unit)
    }
  }

}
