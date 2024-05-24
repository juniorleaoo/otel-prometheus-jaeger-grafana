package io.crud.user.entity

import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Converter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,

    @Column(name = "public_id", length = 36, unique = true, nullable = false)
    val publicId: String,

    @Column(name = "nick", length = 32)
    val nick: String?,

    @Column(name = "name", length = 255, unique = true, nullable = false)
    val name: String,

    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDateTime,

    @Convert(converter = StringListConverter::class)
    @Column(name = "stack", columnDefinition = "text")
    val stack: List<String>?,
) {
    constructor() : this(null, "", "", "", LocalDateTime.now(), null)
}

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        return attribute?.joinToString(";") ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): List<String>{
        return if (dbData?.isNotBlank() == true) dbData.split(";") else listOf()
    }
}