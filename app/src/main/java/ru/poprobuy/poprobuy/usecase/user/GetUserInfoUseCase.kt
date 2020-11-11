package ru.poprobuy.poprobuy.usecase.user

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.model.api.user.User
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkResource

class GetUserInfoUseCase(
  private val userRepository: UserRepository,
) {

  suspend operator fun invoke(): UseCaseResult<User, Unit> = when (val result = userRepository.fetchUser()) {
    is NetworkResource.Success -> {
      i { "User info fetched successfully" }
      userRepository.saveUser(result.data)
      UseCaseResult.Success(result.data)
    }
    else -> {
      e { "Generic error while fetching user info" }
      UseCaseResult.Failure(Unit)
    }
  }

}
