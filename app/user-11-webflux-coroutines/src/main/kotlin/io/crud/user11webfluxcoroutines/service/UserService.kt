package io.crud.user11webfluxcoroutines.service

import io.crud.user11webfluxcoroutines.entity.User
import io.crud.user11webfluxcoroutines.exception.NotFoundException
import io.crud.user11webfluxcoroutines.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Transactional
@Service
class UserService(
    val userRepository: UserRepository
) {

    suspend fun findById(id: String) = userRepository.findById(id)

    suspend fun findAll() = userRepository.findAll()

    suspend fun existsById(id: String) = userRepository.existsById(id)

    suspend fun deleteById(id: String) {
        if (!existsById(id)) {
            throw NotFoundException("User with id $id not found")
        }
        return userRepository.deleteById(id)
    }

    suspend fun deleteAll() = userRepository.deleteAll()

    suspend fun save(user: User) = userRepository.save(user)

}