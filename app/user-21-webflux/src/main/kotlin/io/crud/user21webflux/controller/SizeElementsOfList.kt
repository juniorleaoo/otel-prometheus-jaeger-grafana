package io.crud.user21webflux.controller

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [SizeElementsOfListValidator::class])
annotation class SizeElementsOfList(
    val message: String = "Os elementos da lista devem estar entre 1 e {size}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val size: Int = 32
)

class SizeElementsOfListValidator : ConstraintValidator<SizeElementsOfList, List<String>> {

    private var size: Int = 32

    override fun initialize(constraintAnnotation: SizeElementsOfList?) {
        if (constraintAnnotation != null) {
            size = constraintAnnotation.size
        }
    }


    override fun isValid(value: List<String>?, context: ConstraintValidatorContext?): Boolean =
        value == null || value.all { it.isNotBlank() && it.length in 0..size }

}