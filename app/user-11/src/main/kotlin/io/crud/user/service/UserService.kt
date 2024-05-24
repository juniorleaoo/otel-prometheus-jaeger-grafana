package io.crud.user.service

import io.crud.user.entity.User
import io.crud.user.exception.NotFoundException
import io.crud.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Transactional
@Service
class UserService(
    val userRepository: UserRepository
) {

    fun findById(id: String) = userRepository.findById(id)

    fun findAll() = userRepository.findAll()

    fun existsById(id: String) = userRepository.existsById(id)

    fun deleteById(id: String): Mono<Unit> {
        return userRepository.findById(id)
            .flatMap {
                if (it.isPresent) {
                    userRepository.deleteById(id)
                } else {
                    Mono.error(NotFoundException("User not found"))
                }
            }

    }

    fun deleteAll() = userRepository.deleteAll()

    fun save(user: User) = userRepository.save(user)

}