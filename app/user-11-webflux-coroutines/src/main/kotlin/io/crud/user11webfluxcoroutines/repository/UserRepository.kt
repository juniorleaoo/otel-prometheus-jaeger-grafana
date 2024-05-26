package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import java.util.Optional

interface UserRepository {

    suspend fun findByPublicId(publicId: String): Optional<User>
    suspend fun findAll(): Iterable<User>
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(user: User): User

}