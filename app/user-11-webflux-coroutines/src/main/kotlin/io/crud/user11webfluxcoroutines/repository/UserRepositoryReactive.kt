package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Repository
import reactor.core.scheduler.Scheduler
import java.util.Optional

@Repository
class UserRepositoryReactive(
    val userRepository: UserRepositoryJPA,
    val scheduler: Scheduler
) : UserRepository {

    override suspend fun findById(id: String): Optional<User> {
        return coroutineScope { userRepository.findByPublicId(id) }
    }

    override suspend fun findAll(): Iterable<User> {
        return coroutineScope { userRepository.findAll() }
    }

    override suspend fun existsById(id: String): Boolean {
        return coroutineScope { userRepository.existsByPublicId(id) }
    }

    override suspend fun deleteById(id: String) {
        coroutineScope { userRepository.deleteByPublicId(id) }
    }

    override suspend fun deleteAll() {
        coroutineScope { userRepository.deleteAll() }
    }

    override suspend fun save(user: User): User {
        return coroutineScope { userRepository.save(user) }
    }


}