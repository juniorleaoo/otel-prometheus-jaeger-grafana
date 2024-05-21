package io.crud.user.repository

import io.crud.user.entity.User
import reactor.core.publisher.Mono

interface UserRepository {
    fun findById(id: String): Mono<User>
    fun findAll(): Mono<Iterable<User>>
    fun existsById(id: String): Mono<Boolean>
    fun deleteById(id: String): Mono<Unit>
    fun save(user: User): Mono<User>

}