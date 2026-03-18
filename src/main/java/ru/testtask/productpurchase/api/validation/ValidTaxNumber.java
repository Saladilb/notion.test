package ru.testtask.productpurchase.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaxNumberValidator.class)
public @interface ValidTaxNumber {

    String message() default "taxNumber must match one of the supported country formats";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

