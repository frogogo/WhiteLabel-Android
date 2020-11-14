package ru.poprobuy.poprobuy.usecase.user

import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.util.doOnSuccess

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
