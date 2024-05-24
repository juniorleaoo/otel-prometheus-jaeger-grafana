package io.crud.user.controller

import io.crud.user.entity.User
import io.crud.user.exception.NotFoundException
import io.crud.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Mono<ResponseEntity<UserResponse>> {
        return userService.findById(id)
            .flatMap { Mono.justOrEmpty(it) }
            .map { ResponseEntity.ok(it.toResponse()) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorResume(Exception::class.java) {
                Mono.error(NotFoundException("User not found"))
            }
    }

    @GetMapping
    fun list(): Mono<ResponseEntity<List<UserResponse>>> {
        return userService.findAll()
            .map { it.map(User::toResponse) }
            .map { ResponseEntity.ok(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): Mono<ResponseEntity<Void>> {
        return userService.deleteById(id)
            .map { ResponseEntity.noContent().build<Void>() }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @DeleteMapping
    fun deleteAll(): Mono<ResponseEntity<Void>> {
        return userService.deleteAll()
            .map { ResponseEntity.noContent().build() }
    }

    @PostMapping
    fun create(@Valid @RequestBody userRequest: UserRequest): Mono<ResponseEntity<UserResponse>> {
        return userService.save(userRequest.toUser())
            .map {
                ResponseEntity.created(URI.create("/users/${it.publicId}"))
                    .body(it.toResponse())
            }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @Valid @RequestBody userRequest: UserRequest
    ): Mono<ResponseEntity<UserResponse>> {
        return userService.findById(id)
            .flatMap { Mono.justOrEmpty(it) }
            .flatMap {
                userService.save(it.copy(
                    name = userRequest.name,
                    nick = userRequest.nick,
                    birthDate = userRequest.birthDate,
                    stack = userRequest.stack
                ))
            }
            .map { ResponseEntity.ok(it.toResponse()) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }


}