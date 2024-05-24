package io.crud.user11webfluxcoroutines.controller

import io.crud.user11webfluxcoroutines.exception.NotFoundException
import io.crud.user11webfluxcoroutines.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    suspend fun get(@PathVariable("id") id: String): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
            .orElseThrow { NotFoundException("User with id $id not found") }
        return ResponseEntity.ok(user.toResponse())
    }

    @GetMapping
    suspend fun list(): ResponseEntity<List<UserResponse>> {
        val users = userService.findAll()
            .map { it.toResponse() }
        return ResponseEntity.ok(users)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id") id: String): ResponseEntity<Void> {
        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    suspend fun deleteAll(): ResponseEntity<Void> {
        userService.deleteAll()
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    suspend fun create(@Valid @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.save(userRequest.toUser())
        return ResponseEntity
            .created(URI.create("/users/${user.publicId}"))
            .body(user.toResponse())
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable("id") id: String, @Valid @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
            .orElseThrow { NotFoundException("User with id $id not found") }
        val updatedUser = userService.save(user.copy(
            name = userRequest.name,
            nick = userRequest.nick,
            birthDate = userRequest.birthDate,
            stack = userRequest.stack
        ))
        return ResponseEntity.ok(updatedUser.toResponse())
    }

}