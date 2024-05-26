package io.crud.user11webfluxcoroutines.repository

import io.crud.user11webfluxcoroutines.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Transactional
@Repository
interface UserRepositoryJPA: JpaRepository<User, Long> {

    fun findByPublicId(publicId: String): Optional<User>

}