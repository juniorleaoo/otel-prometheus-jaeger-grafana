package io.crud.user21mvc.repository

import io.crud.user21mvc.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {

    fun findByPublicId(publicId: String): Optional<User>

}