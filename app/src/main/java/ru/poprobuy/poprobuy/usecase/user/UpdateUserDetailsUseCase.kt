package ru.poprobuy.poprobuy.usecase.user

import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import ru.poprobuy.poprobuy.data.repository.UserRepository
import ru.poprobuy.poprobuy.usecase.UseCaseResult
import ru.poprobuy.poprobuy.util.network.NetworkResource

class UpdateUserDetailsUseCase(
  private val userRepository: UserRepository,
) {

  suspend operator fun invoke(email: String, name: String): UseCaseResult<Unit, Unit> {
    return when (userRepository.updateUser(email, name)) {
      is NetworkResource.Success -> {
        i { "User info updated successfully" }
        UseCaseResult.Success(Unit)
      }
      is NetworkResource.Error -> {
        e { "Generic error while updating user info" }
        UseCaseResult.Failure(Unit)
      }
    }
  }

}
