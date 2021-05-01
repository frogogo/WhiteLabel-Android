package ru.frogogo.whitelabel.usecase.user

import ru.frogogo.whitelabel.core.doOnSuccess
import ru.frogogo.whitelabel.data.model.api.user.User
import ru.frogogo.whitelabel.data.repository.UserRepository

class GetUserInfoUseCase(
  private val userRepository: UserRepository,
) {

  suspend operator fun invoke() = userRepository.fetchUser().apply {
    doOnSuccess(::handleSuccess)
  }

  private fun handleSuccess(user: User) {
    userRepository.saveUser(user)
  }
}
