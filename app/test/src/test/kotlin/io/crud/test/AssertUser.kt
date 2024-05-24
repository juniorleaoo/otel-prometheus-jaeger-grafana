package io.crud.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

object AssertUser {

    fun equals(expected: User, actual: User) {
        assertNotNull(actual.id)
        assertEquals(expected.birth_date, actual.birth_date)
        assertEquals(expected.nick, actual.nick)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.stack, actual.stack)
    }

}