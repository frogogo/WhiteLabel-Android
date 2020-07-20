package ru.poprobuy.poprobuy.usecase.user

import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkResource

class UpdateUserDetailsUseCase(
  private val userRepository: UserRepository
) {

  suspend operator fun invoke(email: String, name: String): UseCaseResult<Unit, Any> {
    return when (userRepository.updateUser(email, name)) {
      is NetworkResource.Success -> UseCaseResult.Success(Unit)
      is NetworkResource.Error -> UseCaseResult.Failure()
    }
  }

}
