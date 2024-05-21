package io.crud.user.repository

import io.crud.user.entity.User
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler

@Repository
class UserRepositoryReactive(
    val userRepository: UserRepositoryJPA,
    val scheduler: Scheduler
): UserRepository {

    override fun findById(id: String): Mono<User> {
        return async {
            val user = userRepository.findByPublicId(id)
            user
        }
    }

    override fun findAll(): Mono<Iterable<User>> {
        return async { userRepository.findAll() }
    }

    override fun existsById(id: String): Mono<Boolean> {
        return async { userRepository.existsByPublicId(id) }
    }

    @Transactional
    override fun deleteById(id: String): Mono<Unit> {
        return async { userRepository.deleteByPublicId(id) }
    }

    override fun save(user: User): Mono<User> {
        return async { userRepository.save(user) }
    }

    private fun <T> async (block: () -> T): Mono<T> {
        return Mono.fromCallable(block).subscribeOn(scheduler)
    }

}