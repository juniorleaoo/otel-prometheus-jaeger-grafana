package io.crud.test

import io.restassured.RestAssured.given
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.random.Random

class UserTest {

    private val baseUri = "http://localhost/v1/users"

    private fun mockUser(): User {
        val randomNumber = Random.nextInt(1, 1000)
        return User(
            birth_date = "2021-01-01T00:00:00",
            nick = "nick$randomNumber",
            name = "name$randomNumber",
            stack = listOf("Java", "Kotlin")
        )
    }

    private fun createUser(user: User): User {
        val response = given()
            .baseUri(baseUri)
            .port(8080)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(user)
            .post()
        return response.body().jsonPath().getObject("", User::class.java)
    }

    @BeforeEach
    fun setup() {
        given().delete(baseUri)
    }

    @Nested
    inner class Create {

        @Test
        fun `should create user successfully`() {
            val user = mockUser()
            Given {
                baseUri(baseUri)
                port(8080)
                header("Content-Type", "application/json")
                header("Accept", "application/json")
                body(user)
            } When {
                post()
            } Then {
                statusCode(201)
                header("Location", notNullValue())
            } Extract {
                val userResponse = body().jsonPath().getObject("", User::class.java)
                AssertUser.equals(user, userResponse)
            }
        }

        @ParameterizedTest()
        @ValueSource(
            strings = [
                "",
                "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc" +
                        "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc" +
                        "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc" +
                        "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc"
            ]
        )
        fun `should not create user when name is invalid value`(name: String) {
            val user = mockUser().copy(name = name)
            Given {
                baseUri(baseUri)
                port(8080)
                header("Content-Type", "application/json")
                header("Accept", "application/json")
                body(user)
            } When {
                post()
            } Then {
                statusCode(400)
            } Extract {
                val errorsResponse = body().jsonPath().getObject("", ErrorsResponse::class.java)
                assertNotNull(errorsResponse)
                assertEquals(1, errorsResponse.errors.size)
                assertEquals("O campo nome é obrigatório e deve estar entre 1 e 255", errorsResponse.errors[0])
            }
        }

        @Test
        fun `should not create user when have empty value on stack`() {
            val user = mockUser().copy(stack = listOf("", ""))
            Given {
                baseUri(baseUri)
                port(8080)
                header("Content-Type", "application/json")
                header("Accept", "application/json")
                body(user)
            } When {
                post()
            } Then {
                statusCode(400)
            } Extract {
                val errorsResponse = body().jsonPath().getObject("", ErrorsResponse::class.java)
                assertNotNull(errorsResponse)
                assertEquals(1, errorsResponse.errors.size)
                assertEquals("Os elementos da lista devem estar entre 1 e 32", errorsResponse.errors[0])
            }
        }

        @Test
        fun `should create user when not have stack`() {
            val user = mockUser().copy(stack = emptyList())
            Given {
                baseUri(baseUri)
                port(8080)
                header("Content-Type", "application/json")
                header("Accept", "application/json")
                body(user)
            } When {
                post()
            } Then {
                statusCode(201)
            } Extract {
                val userResponse = body().jsonPath().getObject("", User::class.java)
                AssertUser.equals(user, userResponse)
            }
        }

    }

    @Nested
    inner class List {

        @Test
        fun `should empty list users successfully`() {
            Given {
                baseUri(baseUri)
                port(8080)
            } When {
                get()
            } Then {
                statusCode(200)
                body("size()", equalTo(0))
            }
        }

        @Test
        fun `should list users when have one user`() {
            val user = createUser(mockUser())
            Given {
                baseUri(baseUri)
                port(8080)
            } When {
                get()
            } Then {
                statusCode(200)
                body("size()", equalTo(1))
            } Extract {
                val userResponse = body().jsonPath().getObject("[0]", User::class.java)
                AssertUser.equals(user, userResponse)
            }
        }

    }

    @Nested
    inner class GetById {

        @Test
        fun `should get user by id successfully`() {
            val user = createUser(mockUser())
            Given {
                baseUri(baseUri)
                port(8080)
            } When {
                get("/${user.id}")
            } Then {
                statusCode(200)
            } Extract {
                val userResponse = body().jsonPath().getObject("", User::class.java)
                AssertUser.equals(user, userResponse)
            }
        }

        @Test
        fun `should get user that does not exists and return not found`() {
            Given {
                baseUri(baseUri)
                port(8080)
            } When {
                get("/1")
            } Then {
                statusCode(404)
            }
        }

    }

    @Nested
    inner class Delete {

        @Test
        fun `should delete user by id`() {
            val user = createUser(mockUser())
            Given {
                baseUri(baseUri)
                port(8080)
            } When {
                delete("/${user.id}")
            } Then {
                statusCode(204)
            }
        }

        @Test
        fun `should delete user that not exists and return not found`() {
            Given {
                baseUri(baseUri)
                port(8080)
            } When {
                delete("/1")
            } Then {
                statusCode(404)
            }
        }

    }

    @Nested
    inner class Update {

        @Test
        fun `should update user successfully`() {
            val user = createUser(mockUser())
            val userUpdate = user.copy(
                name = "Name 2",
                nick = "Nick 2",
                stack = listOf("Java", "Kotlin", "Python"),
                birth_date = "2021-01-01T00:00:00"
            )
            Given {
                baseUri(baseUri)
                port(8080)
                header("Content-Type", "application/json")
                header("Accept", "application/json")
                body(userUpdate)
            } When {
                put("/${user.id}")
            } Then {
                statusCode(200)
            } Extract {
                val userResponse = body().jsonPath().getObject("", User::class.java)
                AssertUser.equals(userUpdate, userResponse)
            }
        }

    }

}
