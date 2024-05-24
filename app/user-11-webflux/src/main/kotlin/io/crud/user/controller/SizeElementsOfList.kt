package io.crud.user.controller

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
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