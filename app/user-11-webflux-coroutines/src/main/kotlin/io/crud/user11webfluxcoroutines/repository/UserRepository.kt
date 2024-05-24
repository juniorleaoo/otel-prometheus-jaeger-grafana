package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Optional

interface UserRepository {

    suspend fun findById(id: String): Optional<User>
    suspend fun findAll(): Iterable<User>
    suspend fun existsById(id: String): Boolean
    suspend fun deleteById(id: String)
    suspend fun deleteAll()
    suspend fun save(user: User): User

}