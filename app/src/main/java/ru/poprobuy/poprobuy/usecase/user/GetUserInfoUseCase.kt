package ru.poprobuy.poprobuy.usecase.user

import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkResource

class GetUserInfoUseCase(
  private val userRepository: UserRepository
) {

  suspend operator fun invoke(): UseCaseResult<User, Any> = when (val result = userRepository.fetchUser()) {
    is NetworkResource.Success -> {
      userRepository.saveUser(result.data)
      UseCaseResult.Success(result.data)
    }
    else -> UseCaseResult.Failure()
  }

}
