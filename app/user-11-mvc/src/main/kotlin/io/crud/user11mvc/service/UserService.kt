package io.crud.user11mvc.service

import io.crud.user11mvc.entity.User
import io.crud.user11mvc.exception.NotFoundException
import io.crud.user11mvc.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    val userRepository: UserRepository
) {

    fun findById(id: String): User {
        return userRepository.findByPublicId(id)
            .orElseThrow { NotFoundException("User with id $id not found") }
    }

    fun findAll() = userRepository.findAll()

    fun deleteById(id: String) {
        val user = findById(id)
        return userRepository.deleteById(user.id!!)
    }

    fun deleteAll() = userRepository.deleteAll()

    fun save(user: User) = userRepository.save(user)

}