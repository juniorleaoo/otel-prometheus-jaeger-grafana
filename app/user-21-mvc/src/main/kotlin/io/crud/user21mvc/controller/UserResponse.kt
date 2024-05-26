package io.crud.user21mvc.controller

import io.crud.user21mvc.entity.User
import java.time.LocalDateTime

data class UserResponse(
    val id: String,
    val birthDate: LocalDateTime,
    val nick: String?,
    val name: String,
    val stack: List<String>?,
)

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = publicId,
        birthDate = birthDate,
        nick = nick,
        name = name,
        stack = stack
    )
}