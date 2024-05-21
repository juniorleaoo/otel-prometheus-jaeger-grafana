package io.crud.user.service

import io.crud.user.entity.User
import io.crud.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserService(
    val userRepository: UserRepository
) {

    fun findById(id: String) = userRepository.findById(id)

    fun findAll() = userRepository.findAll()

    fun existsById(id: String) = userRepository.existsById(id)

    fun deleteById(id: String) = userRepository.deleteById(id)

    fun save(user: User) = userRepository.save(user)

}