package io.crud.test

data class User(
    val id: String? = null,
    val birth_date: String,
    val nick: String,
    val name: String,
    val stack: List<String>,
) {
    constructor() : this(
        birth_date = "",
        nick = "",
        name = "",
        stack = emptyList()
    )
}

data class ErrorsResponse(
    var errors: List<String>
) {
    constructor() : this(mutableListOf())
}