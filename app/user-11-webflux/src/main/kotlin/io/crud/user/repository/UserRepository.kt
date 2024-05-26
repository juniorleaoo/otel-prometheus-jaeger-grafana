package io.crud.user.repository

import io.crud.user.entity.User
import reactor.core.publisher.Mono
import java.util.Optional

interface UserRepository {
    fun findById(id: String): Mono<Optional<User>>
    fun findAll(): Mono<Iterable<User>>
    fun deleteById(id: String): Mono<Unit>
    fun deleteAll(): Mono<Unit>
    fun save(user: User): Mono<User>
}