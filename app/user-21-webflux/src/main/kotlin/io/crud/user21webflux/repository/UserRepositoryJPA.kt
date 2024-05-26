package io.crud.user21webflux.repository

import io.crud.user21webflux.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Transactional
@Repository
interface UserRepositoryJPA: JpaRepository<User, Long> {

    fun findByPublicId(publicId: String): Optional<User>

    fun deleteByPublicId(publicId: String)

}