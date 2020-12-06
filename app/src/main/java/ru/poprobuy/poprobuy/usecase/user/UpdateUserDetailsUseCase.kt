package ru.poprobuy.poprobuy.usecase.user

import ru.poprobuy.poprobuy.data.model.api.ErrorResponse
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.core.Result
import ru.poprobuy.poprobuy.util.network.NetworkError

class UpdateUserDetailsUseCase(
  private val userRepository: UserRepository,
) {

  suspend operator fun invoke(email: String, name: String): Result<Unit, NetworkError<ErrorResponse>> =
    userRepository.updateUser(email, name)

}
