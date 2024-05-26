package io.crud.user.repository

import io.crud.user.entity.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import java.util.Optional

@Repository
class UserRepositoryReactive(
    val userRepository: UserRepositoryJPA,
    val scheduler: Scheduler
): UserRepository {

    override fun findById(id: String): Mono<Optional<User>> {
        return async { userRepository.findByPublicId(id) }
    }

    override fun findAll(): Mono<Iterable<User>> {
        return async { userRepository.findAll() }
    }

    @Transactional
    override fun deleteById(id: String): Mono<Unit> {
        return async { userRepository.deleteByPublicId(id) }
    }

    override fun deleteAll(): Mono<Unit> {
        return async { userRepository.deleteAll() }
    }

    override fun save(user: User): Mono<User> {
        return async { userRepository.save(user) }
    }

    private fun <T> async (block: () -> T): Mono<T> {
        return Mono.fromCallable(block).subscribeOn(scheduler)
    }

}