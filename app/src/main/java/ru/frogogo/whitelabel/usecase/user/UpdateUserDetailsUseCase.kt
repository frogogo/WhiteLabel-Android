package ru.frogogo.whitelabel.usecase.user

import ru.frogogo.whitelabel.data.model.api.ErrorResponse
import ru.frogogo.whitelabel.data.repository.UserRepository
import ru.frogogo.whitelabel.core.Result
import ru.frogogo.whitelabel.util.network.NetworkError

class UpdateUserDetailsUseCase(
  private val userRepository: UserRepository,
) {

  suspend operator fun invoke(email: String, name: String): Result<Unit, NetworkError<ErrorResponse>> =
    userRepository.updateUser(email, name)
}
